
package com.loopcupcakes.gassy.entities.places;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Geometry {

    @SerializedName("location")
    @Expose
    private Loc loc;

    /**
     * 
     * @return
     *     The loc
     */
    public Loc getLoc() {
        return loc;
    }

    /**
     * 
     * @param loc
     *     The loc
     */
    public void setLoc(Loc loc) {
        this.loc = loc;
    }

    @Override
    public String toString() {
        return "Geometry{" +
                "loc=" + loc +
                '}';
    }
}
