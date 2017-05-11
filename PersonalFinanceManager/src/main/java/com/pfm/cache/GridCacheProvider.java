/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.cache;

import com.google.gson.Gson;
import com.pfm.filemanager.FileManager;
import com.pfm.personalfinancemanagergrid.cache.GridCacheObject;
import com.pfm.personalfinancemanagergrid.cache.ICacheProvider;
import java.io.File;
import javax.servlet.ServletContext;

/**
 *
 * @author mihail
 */
public class GridCacheProvider implements ICacheProvider {

    private ServletContext context;

    public GridCacheProvider(ServletContext context) {
        this.context = context;
    }

    @Override
    public void setCache(String key, GridCacheObject object) {
        Gson gson = new Gson();
        String cache = gson.toJson(object);
        FileManager.createFile("assets/tmp/cache/grid/", key + ".gc", context, cache);
    }

    @Override
    public GridCacheObject getCache(String key) {
        Gson gson = new Gson();
        String cacheString = FileManager.getFileContent("assets/tmp/cache/grid/" + key + ".gc", context);
        GridCacheObject cache = gson.fromJson(cacheString, GridCacheObject.class);
        return cache;
    }
}
