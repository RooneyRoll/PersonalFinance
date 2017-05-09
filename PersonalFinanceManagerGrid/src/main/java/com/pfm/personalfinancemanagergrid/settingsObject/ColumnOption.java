/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.personalfinancemanagergrid.settingsObject;

/**
 *
 * @author Misho
 */
public class ColumnOption {
    private String optionContent;
    private String objectIdSource; 
    private String optionHref;

    public ColumnOption(String optionContent,String idSource,String optHref){
        this.optionContent = optionContent;
        this.objectIdSource = idSource;
        this.optionHref = optHref;
    }

    public String getOptionHref() {
        return optionHref;
    }

    public void setOptionHref(String optionHref) {
        this.optionHref = optionHref;
    }

    public String getObjectIdSource() {
        return objectIdSource;
    }

    public void setObjectIdSource(String objectIdSource) {
        this.objectIdSource = objectIdSource;
    }
    
    public String getOptionContent() {
        return optionContent;
    }

    public void setOptionContent(String optionContent) {
        this.optionContent = optionContent;
    }
    
}
