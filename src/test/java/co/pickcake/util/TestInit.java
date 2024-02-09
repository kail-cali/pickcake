package co.pickcake.util;

import co.pickcake.testconfig.InitCreate;
import co.pickcake.testconfig.TestDataItem;
import co.pickcake.testconfig.TestDataSize;

public interface TestInit {

    public void preinit(InitCreate initCreate);
    public TestDataSize dbInitWithDynamic();
    public TestDataSize dbInitWithMember();
    public TestDataSize dbInitWithItems();

    public TestDataItem dbInitWithSingleItem();


}
