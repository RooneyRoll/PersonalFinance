/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.data.sets;

import com.pfm.data.data.CategoryBudgetData;
import com.pfm.data.entities.CategoryBudget;
import com.pfm.data.sets.base.IManagable;
import com.pfm.data.sets.base.IViewable;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author Admin
 */
public interface ICategoryBudgetSet extends IViewable<CategoryBudget, UUID>, IManagable<CategoryBudgetData, UUID> {

    @Override
    public UUID Add(CategoryBudgetData data);
    
    @Override
    public void Edit(UUID id,CategoryBudgetData data);
    
    public List<CategoryBudget> GetAllActiveCategoryBudgetsByBudgetId(UUID budgetId);
    
}
