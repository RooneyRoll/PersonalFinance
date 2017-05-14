/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.data.sets;

import com.pfm.data.data.CategoryDetailData;
import com.pfm.data.entities.CategoryDetail;
import com.pfm.data.sets.base.IManagable;
import com.pfm.data.sets.base.IViewable;
import java.util.UUID;

/**
 *
 * @author Admin
 */
public interface ICategoryDetailSet extends IViewable<CategoryDetail, UUID>, IManagable<CategoryDetailData, UUID> {

    @Override
    public UUID Add(CategoryDetailData data);
    
    @Override
    public void Edit(UUID id,CategoryDetailData data) ;
    
}
