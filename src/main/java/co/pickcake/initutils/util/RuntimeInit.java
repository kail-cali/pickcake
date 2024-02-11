package co.pickcake.initutils.util;

import co.pickcake.initutils.testconfig.RuntimeDataItem;
import co.pickcake.initutils.testconfig.RuntimeTestDataSize;

public interface RuntimeInit {

    public void preinit();
    public RuntimeTestDataSize dbInitWithDynamic();
    public RuntimeTestDataSize dbInitWithMember();
    public RuntimeTestDataSize dbInitWithItems();
    public RuntimeDataItem dbInitWithSingleItem();

}
