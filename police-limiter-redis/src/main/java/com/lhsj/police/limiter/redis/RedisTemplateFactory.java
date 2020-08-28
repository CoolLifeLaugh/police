package com.lhsj.police.limiter.redis;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lhsj.police.limiter.redis.exception.RedisUrlSyntaxException;
import io.lettuce.core.ClientOptions;
import io.lettuce.core.TimeoutOptions;
import io.lettuce.core.cluster.ClusterClientOptions;
import io.lettuce.core.cluster.ClusterTopologyRefreshOptions;
import io.lettuce.core.resource.ClientResources;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.data.redis.LettuceClientConfigurationBuilderCustomizer;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.StringUtils;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static com.lhsj.police.core.base.Validate.isTrue;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class RedisTemplateFactory {

    private RedisProperties                                             properties;
    /**
     * redis配置, sentinel哨兵类型
     */
    private RedisSentinelConfiguration                                  sentinelConfiguration;
    /**
     * redis配置，cluster集群配置
     */
    private RedisClusterConfiguration                                   clusterConfiguration;
    /**
     * redis client配置
     */
    private ClientResources                                             clientResources;
    /**
     * 配置扩展
     */
    private ObjectProvider<LettuceClientConfigurationBuilderCustomizer> builderCustomizers;

    public void setProperties(RedisProperties properties) {
        this.properties = properties;
    }

    public RedisSentinelConfiguration getSentinelConfiguration() {
        return sentinelConfiguration;
    }

    public void setSentinelConfiguration(RedisSentinelConfiguration sentinelConfiguration) {
        this.sentinelConfiguration = sentinelConfiguration;
    }

    public void setClusterConfiguration(RedisClusterConfiguration clusterConfiguration) {
        this.clusterConfiguration = clusterConfiguration;
    }

    public ClientResources getClientResources() {
        return clientResources;
    }

    public void setClientResources(ClientResources clientResources) {
        this.clientResources = clientResources;
    }

    public ObjectProvider<LettuceClientConfigurationBuilderCustomizer> getBuilderCustomizers() {
        return builderCustomizers;
    }

    public void setBuilderCustomizers(ObjectProvider<LettuceClientConfigurationBuilderCustomizer> builderCustomizers) {
        this.builderCustomizers = builderCustomizers;
    }

    public RedisTemplateFactory() {
    }

    public static RedisTemplateFactory of() {
        return new RedisTemplateFactory();
    }

    public RedisTemplateFactory properties(RedisProperties properties) {
        this.properties = properties;
        return this;
    }

    public RedisTemplateFactory sentinelConfiguration(RedisSentinelConfiguration sentinelConfiguration) {
        this.sentinelConfiguration = sentinelConfiguration;
        return this;
    }

    public RedisTemplateFactory clusterConfiguration(RedisClusterConfiguration clusterConfiguration) {
        this.clusterConfiguration = clusterConfiguration;
        return this;
    }

    public RedisTemplateFactory clientResources(ClientResources clientResources) {
        this.clientResources = clientResources;
        return this;
    }

    public RedisTemplateFactory builderCustomizers(ObjectProvider<LettuceClientConfigurationBuilderCustomizer> builderCustomizers) {
        this.builderCustomizers = builderCustomizers;
        return this;
    }

    public RedisTemplate<String, Object> create() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();

        LettuceConnectionFactory lettuceConnectionFactory = redisConnectionFactory();
        // 必须执行初始化
        lettuceConnectionFactory.afterPropertiesSet();

        template.setConnectionFactory(lettuceConnectionFactory);

        // 使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值（默认使用JDK的序列化方式）
        Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(Object.class);

        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        serializer.setObjectMapper(mapper);

        template.setValueSerializer(serializer);

        // 使用StringRedisSerializer来序列化和反序列化redis的key值
        template.setKeySerializer(new StringRedisSerializer());

        // 必须执行初始化
        template.afterPropertiesSet();

        return template;
    }

    public LettuceConnectionFactory redisConnectionFactory() {
        LettuceClientConfiguration clientConfig = getLettuceClientConfiguration(
                builderCustomizers,
                clientResources,
                getProperties().getLettuce().getPool());

        return createLettuceConnectionFactory(clientConfig);
    }

    private LettuceConnectionFactory createLettuceConnectionFactory(LettuceClientConfiguration clientConfiguration) {
        if (getSentinelConfig() != null) {
            return new LettuceConnectionFactory(getSentinelConfig(), clientConfiguration);
        }
        if (getClusterConfiguration() != null) {
            return new LettuceConnectionFactory(getClusterConfiguration(), clientConfiguration);
        }
        return new LettuceConnectionFactory(getStandaloneConfig(), clientConfiguration);
    }

    private LettuceClientConfiguration getLettuceClientConfiguration(
            ObjectProvider<LettuceClientConfigurationBuilderCustomizer> builderCustomizers,
            ClientResources clientResources,
            RedisProperties.Pool pool) {
        LettuceClientConfiguration.LettuceClientConfigurationBuilder builder = createBuilder(pool);

        applyProperties(builder);

        if (StringUtils.hasText(getProperties().getUrl())) {
            customizeConfigurationFromUrl(builder);
        }

        if (nonNull(clientResources)) {
            builder.clientResources(clientResources);
        }

        builder.clientOptions(initializeClientOptionsBuilder().timeoutOptions(TimeoutOptions.enabled()).build());

        if (nonNull(builderCustomizers)) {
            builderCustomizers.orderedStream().forEach((customizer) -> customizer.customize(builder));
        }

        return builder.build();
    }

    private LettuceClientConfiguration.LettuceClientConfigurationBuilder createBuilder(RedisProperties.Pool pool) {
        if (isNull(pool)) {
            return LettuceClientConfiguration.builder();
        }

        return new PoolBuilderFactory().createBuilder(pool);
    }

    private LettuceClientConfiguration.LettuceClientConfigurationBuilder applyProperties(
            LettuceClientConfiguration.LettuceClientConfigurationBuilder builder) {
        if (getProperties().isSsl()) {
            builder.useSsl();
        }

        if (getProperties().getTimeout() != null) {
            builder.commandTimeout(getProperties().getTimeout());
        }

        if (getProperties().getLettuce() != null) {
            RedisProperties.Lettuce lettuce = getProperties().getLettuce();
            if (lettuce.getShutdownTimeout() != null && !lettuce.getShutdownTimeout().isZero()) {
                builder.shutdownTimeout(getProperties().getLettuce().getShutdownTimeout());
            }
        }

        if (StringUtils.hasText(getProperties().getClientName())) {
            builder.clientName(getProperties().getClientName());
        }

        return builder;
    }

    private ClientOptions.Builder initializeClientOptionsBuilder() {
        if (getProperties().getCluster() != null) {
            ClusterClientOptions.Builder builder = ClusterClientOptions.builder();
            RedisProperties.Lettuce.Cluster.Refresh refreshProperties = getProperties().getLettuce().getCluster().getRefresh();
            ClusterTopologyRefreshOptions.Builder refreshBuilder = ClusterTopologyRefreshOptions.builder();
            if (refreshProperties.getPeriod() != null) {
                refreshBuilder.enablePeriodicRefresh(refreshProperties.getPeriod());
            }
            if (refreshProperties.isAdaptive()) {
                refreshBuilder.enableAllAdaptiveRefreshTriggers();
            }
            return builder.topologyRefreshOptions(refreshBuilder.build());
        }
        return ClientOptions.builder();
    }

    private void customizeConfigurationFromUrl(LettuceClientConfiguration.LettuceClientConfigurationBuilder builder) {
        ConnectionInfo connectionInfo = parseUrl(getProperties().getUrl());
        if (connectionInfo.isUseSsl()) {
            builder.useSsl();
        }
    }

    protected final RedisStandaloneConfiguration getStandaloneConfig() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();

        if (StringUtils.hasText(this.properties.getUrl())) {
            ConnectionInfo connectionInfo = parseUrl(this.properties.getUrl());
            config.setHostName(connectionInfo.getHostName());
            config.setPort(connectionInfo.getPort());
            config.setPassword(RedisPassword.of(connectionInfo.getPassword()));
        } else {
            config.setHostName(this.properties.getHost());
            config.setPort(this.properties.getPort());
            config.setPassword(RedisPassword.of(this.properties.getPassword()));
        }

        config.setDatabase(this.properties.getDatabase());

        return config;
    }

    protected final RedisSentinelConfiguration getSentinelConfig() {
        if (this.sentinelConfiguration != null) {
            return this.sentinelConfiguration;
        }

        RedisProperties.Sentinel sentinelProperties = this.properties.getSentinel();

        if (isNull(sentinelProperties)) {
            return null;
        }

        RedisSentinelConfiguration config = new RedisSentinelConfiguration();
        config.master(sentinelProperties.getMaster());
        config.setSentinels(createSentinels(sentinelProperties));

        if (nonNull(this.properties.getPassword())) {
            config.setPassword(RedisPassword.of(this.properties.getPassword()));
        }

        if (nonNull(sentinelProperties.getPassword())) {
            config.setSentinelPassword(RedisPassword.of(sentinelProperties.getPassword()));
        }

        config.setDatabase(this.properties.getDatabase());
        return config;
    }

    /**
     * Create a {@link RedisClusterConfiguration} if necessary.
     *
     * @return {@literal null} if no cluster settings are set.
     */
    protected final RedisClusterConfiguration getClusterConfiguration() {
        if (this.clusterConfiguration != null) {
            return this.clusterConfiguration;
        }
        if (this.properties.getCluster() == null) {
            return null;
        }

        RedisProperties.Cluster clusterProperties = this.properties.getCluster();
        RedisClusterConfiguration config = new RedisClusterConfiguration(clusterProperties.getNodes());

        if (clusterProperties.getMaxRedirects() != null) {
            config.setMaxRedirects(clusterProperties.getMaxRedirects());
        }

        if (this.properties.getPassword() != null) {
            config.setPassword(RedisPassword.of(this.properties.getPassword()));
        }

        return config;
    }

    protected final RedisProperties getProperties() {
        return this.properties;
    }

    private List<RedisNode> createSentinels(RedisProperties.Sentinel sentinel) {
        List<RedisNode> nodes = new ArrayList<>();
        for (String node : sentinel.getNodes()) {
            try {
                String[] parts = StringUtils.split(node, ":");

                isTrue(nonNull(parts), "node null");
                isTrue(parts.length == 2, "Must be defined as 'host:port'");

                nodes.add(new RedisNode(parts[0], Integer.parseInt(parts[1])));
            } catch (RuntimeException ex) {
                throw new IllegalStateException("Invalid redis sentinel property '" + node + "'", ex);
            }
        }
        return nodes;
    }

    protected ConnectionInfo parseUrl(String url) {
        try {
            URI uri = new URI(url);
            String scheme = uri.getScheme();
            if (!"redis".equals(scheme) && !"rediss".equals(scheme)) {
                throw new RedisUrlSyntaxException(url);
            }
            boolean useSsl = ("rediss".equals(scheme));
            String password = null;
            if (uri.getUserInfo() != null) {
                password = uri.getUserInfo();
                int index = password.indexOf(':');
                if (index >= 0) {
                    password = password.substring(index + 1);
                }
            }
            return new ConnectionInfo(uri, useSsl, password);
        } catch (URISyntaxException ex) {
            throw new RedisUrlSyntaxException(url, ex);
        }
    }

    static class ConnectionInfo {

        private final URI     uri;
        private final boolean useSsl;
        private final String  password;

        ConnectionInfo(URI uri, boolean useSsl, String password) {
            this.uri = uri;
            this.useSsl = useSsl;
            this.password = password;
        }

        boolean isUseSsl() {
            return this.useSsl;
        }

        String getHostName() {
            return this.uri.getHost();
        }

        int getPort() {
            return this.uri.getPort();
        }

        String getPassword() {
            return this.password;
        }
    }

    /**
     * Inner class to allow optional commons-pool2 dependency.
     */
    private static class PoolBuilderFactory {

        LettuceClientConfiguration.LettuceClientConfigurationBuilder createBuilder(RedisProperties.Pool properties) {
            return LettucePoolingClientConfiguration.builder().poolConfig(getPoolConfig(properties));
        }

        private GenericObjectPoolConfig<?> getPoolConfig(RedisProperties.Pool properties) {
            GenericObjectPoolConfig<?> config = new GenericObjectPoolConfig<>();
            config.setMaxTotal(properties.getMaxActive());
            config.setMaxIdle(properties.getMaxIdle());
            config.setMinIdle(properties.getMinIdle());
            if (properties.getTimeBetweenEvictionRuns() != null) {
                config.setTimeBetweenEvictionRunsMillis(properties.getTimeBetweenEvictionRuns().toMillis());
            }
            if (properties.getMaxWait() != null) {
                config.setMaxWaitMillis(properties.getMaxWait().toMillis());
            }
            return config;
        }

    }
}
