package quaternary.youreanexpertharry.heck.tasks;

import quaternary.youreanexpertharry.heck.Heckception;

public interface IHeckTask {

    String execute() throws Heckception;

    boolean equals(Object obj);

    int hashCode();


}
