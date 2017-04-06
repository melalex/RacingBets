package com.room414.racingbets.web.controller.factories;

import com.room414.racingbets.web.controller.impl.ViewController;

/**
 * @author Alexander Melashchenko
 * @version 1.0 06 Apr 2017
 */
public class ViewControllerFactory {
    private ViewController viewController = new ViewController();

    public ViewController create() {
        return viewController;
    }

}
