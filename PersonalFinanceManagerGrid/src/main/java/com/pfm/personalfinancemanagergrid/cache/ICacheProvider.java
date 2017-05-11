/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.personalfinancemanagergrid.cache;

/**
 *
 * @author mihail
 */
public interface ICacheProvider {
    public void setCache(String key, GridCacheObject object);
    public GridCacheObject getCache(String key);
}
