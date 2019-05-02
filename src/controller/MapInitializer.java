/**
 * @author: Felipe Mantovani 2017192
 * @date: 2/5/2019
 */
package controller;

import java.util.Map;

/**
 * Interface to force subclasses to initialise their fields called titleMap and MemberMap
 */
public interface MapInitializer {

    /**
     * titleMap field initializer
     * @return
     */
    Map<String, String> initTitleMap();

    /**
     * memberMap field initializer
     * @return
     */
    Map<String, String> initMemberMap();

}
