import com.lhsj.police.core.cron.CronExpression;

import java.text.ParseException;

public class CronExpressionTest {

    public static void main(String[] args) throws ParseException {
        CronExpression expression = new CronExpression("0 0 0 * 1/1 ?");
//        Date d1 = expression.getNextValidTimeAfter(new Date());
//        System.out.println(Dates.dateTime(d1));
//        System.out.println(Dates.dateTime(expression.getNextValidTimeAfter(d1)));
//        System.out.println(Dates.dateTime(Dates.day(d1, -1)));
    }

}
