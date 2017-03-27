package com.room414.racingbets.web.model.infrastructure;

import java.io.Serializable;

/**
 * @author Alexander Melashchenko
 * @version 1.0 27 Mar 2017
 */
public class Route implements Serializable {
    private static final long serialVersionUID = -879039519582965243L;

    private String pattern;
    private String controller;
    private String action;

    public Route(String pattern, String controller, String action) {
        this.pattern = pattern;
        this.controller = controller;
        this.action = action;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getController() {
        return controller;
    }

    public void setController(String controller) {
        this.controller = controller;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Route route = (Route) o;

        if (pattern != null ? !pattern.equals(route.pattern) : route.pattern != null) return false;
        if (controller != null ? !controller.equals(route.controller) : route.controller != null) return false;
        return action != null ? action.equals(route.action) : route.action == null;
    }

    @Override
    public int hashCode() {
        int result = pattern != null ? pattern.hashCode() : 0;
        result = 31 * result + (controller != null ? controller.hashCode() : 0);
        result = 31 * result + (action != null ? action.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Route{" +
                "pattern='" + pattern + '\'' +
                ", controller='" + controller + '\'' +
                ", action='" + action + '\'' +
                '}';
    }
}
