package common;

import play.libs.Akka;
import scala.concurrent.ExecutionContext;

/**
 * Created by patrik on 17.06.2014.
 */
public class DBExecutionContext {
    public static ExecutionContext ctx = Akka.system().dispatchers().lookup("akka.db-dispatcher");
}
