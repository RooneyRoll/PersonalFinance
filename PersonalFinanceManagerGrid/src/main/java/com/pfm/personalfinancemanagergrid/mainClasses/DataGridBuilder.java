/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.personalfinancemanagergrid.mainClasses;

import com.pfm.personalfinancemanagergrid.cache.GridCacheColumnObject;
import com.pfm.personalfinancemanagergrid.cache.GridCacheColumnOption;
import com.pfm.personalfinancemanagergrid.cache.GridCacheObject;
import com.pfm.personalfinancemanagergrid.cache.GridCacheTableWhereObject;
import com.pfm.personalfinancemanagergrid.cache.ICacheProvider;
import com.pfm.personalfinancemanagergrid.settingsObject.ColumnOption;
import com.pfm.personalfinancemanagergrid.settingsObject.ColumnOptionsObject;
import com.pfm.personalfinancemanagergrid.settingsObject.TableSettingsObject;
import com.pfm.personalfinancemanagergrid.settingsObject.ColumnSettingsObject;
import com.pfm.personalfinancemanagergrid.settingsObject.TableWhereObject;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.persistence.Table;

/**
 *
 * @author Misho
 */
public class DataGridBuilder {

    private Class entity;
    private String gridHtml = "";
    private String table;
    private String jsonFieldsVariable;
    private String columnFilters;
    private String initialWhereJson;
    private TableSettingsObject tableSettings;
    private ColumnOptionsObject columnOptions;
    private ICacheProvider cacheProvider;
    private List<ColumnSettingsObject> columnsSettings = new ArrayList<ColumnSettingsObject>();

    public String getGridHtml() {
        return gridHtml;
    }

    public void appendToGridHtml(String html) {
        String oldHtml = this.getGridHtml();
        this.gridHtml = (oldHtml + html);
    }

    public String buildHtmlForGrid() throws ClassNotFoundException {
        GridCacheObject cache = this.buildCacheObject(this.entity);
        UUID id = UUID.randomUUID();
        cacheProvider.setCache(id.toString(), cache);
        buildFieldsJson(cache);
        this.appendToGridHtml("<table id='grid-" + table + "' class='cell-border stripe' cellspacing='0' width='100%'><thead><tr>");
        String columnsDeclaration = "";
        int columnsLength = this.columnsSettings.size();
        int iteration = 0;
        for (GridCacheColumnObject column : cache.getColumns()) {
            if (column.isOptionsColumn()) {
                this.appendToGridHtml("<th class='options'>" + column.getVisibleName() + "</th>");
                String columnOptionsString = "";
                for (GridCacheColumnOption option : column.getOptions()) {
                    columnOptionsString += "<div data-source=\"" + option.getObjectIdSource() + "\" class=\"grid-option\"><span><a href=\"" + option.getOptionHref() + "\">" + option.getOptionContent() + "</a></span></div>";
                }
                columnsDeclaration += "{'data':null,'targets':-1, 'bSortable':false,'bSearchable':false,'defaultContent':'" + columnOptionsString + "'}";
            } else {
                this.appendToGridHtml("<th>" + column.getVisibleName() + "</th>");
                String visible = "true";
                String sortable = "";
                if (column.isSearchableColumn()) {
                    sortable = "true";
                } else {
                    sortable = "false";
                }
                if (!column.isAllowed()) {
                    visible = "false";
                    sortable = "false";
                }
                columnsDeclaration += "{'data':'" + iteration + "','visible':" + visible + ",'orderable':" + sortable + "}";
            }
            if (iteration < columnsLength) {
                columnsDeclaration += ",";
            }
            iteration++;
        }
        this.appendToGridHtml("</tr></thead></table>");
        this.appendToGridHtml("<script type='text/javascript'>\n"
                + "$(document).ready(function(){\n"
                + "         function getCurrentField(key,jsonFields){"
                + "             var result = null;"
                + "             $(jsonFields).each(function(keyF,val){\n"
                + "                 if(key == val.col && val.search){\n"
                + "                     result = val;\n"
                + "                 }"
                + "             })\n"
                + "             return result;\n"
                + "         }\n"
                + "         function getCurrentFieldByNum(pos,jsonFields){"
                + "             var result = null;"
                + "             $(jsonFields).each(function(keyF,val){\n"
                + "                 if(pos == val.num && val.search){\n"
                + "                     result = val;\n"
                + "                 }"
                + "             })\n"
                + "             return result;\n"
                + "         }\n"
                + "     function buildFilterByType(type,column,title){\n"
                + "         var options = '';\n"
                + "         if(type == 'string'){\n"
                + "		options ='<option  value=\"eq\">Равно на</option>'+\n"
                + "		'<option selected value=\"co\">Съдържа</option>'+\n"
                + "		'<option value=\"st\">Започва с</option>'+\n"
                + "		'<option value=\"en\">Завършва с</option>'\n"
                + "         }\n"
                + "         if(type == 'int'){\n"
                + "		options = '<option value=\"eq\" >Равно на</option>'+\n"
                + "		'<option value=\"lt\">По-малко от</option>'+\n"
                + "		'<option value=\"gt\">По-голямо от</option>'\n"
                + "         }\n"
                + "         if(type == 'date'){\n"
                + "		options = '<option value=\"eq\" selected>Равно на</option>'+\n"
                + "		'<option value=\"lt\">Преди</option>'+\n"
                + "		'<option value=\"gt\">След</option>';\n"
                + "         }\n"
                + "         return '<div class=\"filter-holder\">'+\n"
                + "		//'<div class=\"filter-title\">'+title+'</div>'+\n"
                + "		'<div class=\"filter-type\">'+\n"
                + "		'<select data-column=\"+column+\" data-type=\"'+type+'\" class=\"filter-type-select\">'+\n"
                + "		options+\n"
                + "		'</select>'+\n"
                + "		'</div><div class=\"filter-input\">'+\n"
                + "		'<input data-column=\"'+column+'\" type=\"text\" placeholder=\"&#xf002;&nbsp Търсене по '+title+'\"  />'+\n"
                + "		'</div></div>';\n"
                + "     }"
                + "     var token = $('meta[name=\"_csrf\"]').attr('content'); \n"
                + "     var header = $('meta[name=\"_csrf_header\"]').attr('content');\n"
                + "     var table = $('#grid-" + table + "').DataTable({\n"
                + "         'processing': true,\n"
                + "         'responsive': true,\n"
                + "         'serverSide': true,\n"
                + "         'paging': true,\n"
                + "         'lengthMenu': [ 5, 10, 15, 20, 25 ],\n"
                + "         'searching': true,\n"
                + "         'bLengthChange': false,\n"
                + "         'columns': [\n"
                + columnsDeclaration + "\n"
                + "          ],\n"
                + "         'language':{\n"
                + "             'sProcessing':   'Обработка на резултатите...',\n"
                + "             'sLengthMenu':   'Показване на _MENU_ резултата',\n"
                + "             'sZeroRecords':  'Няма намерени резултати',\n"
                + "             'sInfo':         'Показване на резултати от _START_ до _END_ от общо _TOTAL_',\n"
                + "             'sInfoEmpty':    'Показване на резултати от 0 до 0 от общо 0',\n"
                + "             'sInfoFiltered': '(филтрирани от общо _MAX_ резултата)',\n"
                + "             'sInfoPostFix':  '',\n"
                + "             'sSearch':       'Търсене във всички колони:',\n"
                + "             'sUrl':          '',\n"
                + "             'oPaginate': {\n"
                + "                 'sFirst':    'Първа',\n"
                + "                 'sPrevious': 'Предишна',\n"
                + "                 'sNext':     'Следваща',\n"
                + "                 'sLast':     'Последна'\n"
                + "             }"
                + "         },"
                + "         'rowCallback': function( row, data, index ) {\n"
                + "             var source = $(row).find('.grid-option').attr('data-source');"
                + "             var id = data[source];"
                + "             $(row).find('.grid-option a').each(function(key,val){"
                + "                 var link = $(val).attr('href');"
                + "                 link = link.replace('{'+source+'}', id);"
                + "                 $(this).attr('href',link);"
                + "             });"
                + "         },"
                + "         'ajax': {\n"
                + "             'url': 'gridData',\n"
                + "             'type': 'POST',\n"
                + "             'async': true,\n"
                + "             'method':'post',\n"
                + "             'contentType': 'application/json',\n"
                + "             beforeSend: function (xhr,settings){\n"
                + "                 settings.data = JSON.parse(settings.data);"
                + "                 settings.data.cid = \"" + id + "\";\n"
                + "                 settings.data = JSON.stringify(settings.data); "
                + "                 xhr.setRequestHeader('X-CSRF-TOKEN', token);\n"
                + "             },\n"
                + "             data : function ( d ) {\n"
                + "                 var columns = (d['columns']);\n"
                + "                 var filters = " + this.columnFilters + ";\n"
                + "                 delete d.search;"
                + "                 $(columns).each(function(key,value){\n"
                + "                     delete d['columns'][key]['name'];\n"
                + "                     delete d['columns'][key]['searchable'];\n"
                + "                     delete d['columns'][key]['orderable'];\n"
                + "                     if($('.filter-holder').length <= 0){"
                + "                         var jsonFields = " + this.jsonFieldsVariable + ";\n"
                + "                         var currentField = getCurrentField(key,jsonFields);\n"
                + "                         if(currentField != null){"
                + "                             var filterObject = {'value':'','type':currentField.col};"
                + "                             d['columns'][currentField.col]['filter'] = filterObject;\n"
                + "                         }"
                + "                     }"
                + "                     if($('.filter-holder').length > 0){\n"
                + "                         var jsonFields = " + this.jsonFieldsVariable + ";\n"
                + "                         $('.filter-holder').each(function(key,val){\n"
                + "                             var colNum = $(this).find('.filter-input input').attr('data-column');"
                + "                             var currentField = getCurrentField(colNum,jsonFields)\n"
                + "                             if(currentField != null){"
                + "                                 var filterVal = $(this).find('option:selected').val();\n"
                + "                                 var filterType = $(this).find('option:selected').parent().attr('data-type');\n"
                + "                                 var filterObject = {'value':filterVal,'type':currentField.col};\n"
                + "                                 d['columns'][currentField.col]['filter'] = filterObject;\n"
                + "                             }"
                + "                         });\n"
                + "                     }"
                + "                 });\n"
                + "                 return JSON.stringify(d);\n"
                + "             }\n"
                + "         },"
                + "         'fnInitComplete': function(oSettings, json) {\n"
                + "         $('#grid-" + table + " thead').append('<tr class=\"grid-filters\"></tr>');\n"
                + "         $('#grid-" + table + " th:not(.options)').each( function (key,val) {\n"
                + "             var title = $('#grid-" + table + " th').eq( $(this).index()).text();\n"
                + "             var jsonFields = " + this.jsonFieldsVariable + ";\n"
                + "console.log(jsonFields);"
                + "             var currentField = getCurrentFieldByNum(key,jsonFields);"
                + "console.log(currentField);"
                + "             if(currentField != null && currentField.search == true){\n"
                + "                 var filter = buildFilterByType(currentField.type,currentField.col,title);\n"
                + "                 $(this).append(filter);\n"
                + "             }\n"
                + "         });"
                + "         $('.filter-holder input').on( \"keyup change\", function () {\n"
                + "             table \n"
                + "                 .column($(this).attr(\"data-column\"))\n"
                + "                 .search($(this).val())\n"
                + "                 .draw();\n"
                + "         });"
                + "         $('.filter-holder select').each(function(){\n"
                + "             var type = $(this).attr('data-type');"
                + "             if(type =='date'){\n"
                + "                 var filterInput = $(this).parent().parent().find('.filter-input input');"
                + "                 filterInput.wrap(\"<div class='flatpickr'></div>\");"
                + "                 filterInput.attr('data-input','');"
                + "                 filterInput.parent().append('<a class=\"input-button toggle-button\" title=\"toggle\" data-toggle>'+\n"
                + "                     '<i class=\"fa fa-calendar\" aria-hidden=\"true\"></i>'+\n"
                + "                 '</a>'+\n"
                + "                 '<a class=\"input-button remove-button\" title=\"clear\" data-clear>'+\n"
                + "                     '<i class=\"fa fa-times\" aria-hidden=\"true\"></i>'+\n"
                + "                 '</a>');"
                + "                 $(filterInput.parent()).flatpickr({\n"
                + "                     'locale': 'bg',\n"
                + "                     'mode': 'single',"
                + "                     'enableTime':true,\n"
                + "                     'enableTime': true\n,"
                + "                     'wrap':true\n,"
                + "                     'dateFormat': 'Y-m-d h:m:S K'"
                + "                 });\n"
                + "             }\n"
                + "         })\n"
                + "         $('.dataTable thead th').unbind('click.DT');"
                + "         $('.dataTable thead th:not(.options)').click(function(e) {\n"
                + "             var clicked = $(e.target);"
                + "             if (!($(e.target).is('th' ))){ \n"
                + "             } else { \n"
                + "                 var columnIndex = $(this).find('.filter-input input').attr('data-column');\n"
                + "                 var orderType = 'desc';\n"
                + "                 var sortedBy = clicked.attr('aria-sort');\n"
                + "                 if(typeof sortedBy == 'undefined' || sortedBy == 'ascending'){\n"
                + "                     orderType = 'desc';\n"
                + "                 }else{\n"
                + "                     orderType = 'asc';\n"
                + "                 }\n"
                + "                 console.log(orderType);"
                + "                 table\n"
                + "                     .order( [ columnIndex, orderType ] )\n"
                + "                     .draw();"
                + "             }\n"
                + "         });"
                + "         $('.filter-holder select').each(function(){\n"
                + "             $(this).select2({minimumResultsForSearch: -1});"
                + "             $('.filter-type-select input').on('change', function(e) {\n"
                + "                 table \n"
                + "                     .column($(this).attr('data-column'))\n"
                + "                     .search($(this).parent().parent().parent().find('input').val())\n"
                + "                     .draw();\n"
                + "                 });\n"
                + "             });\n"
                + "         }\n"
                + "     })\n"
                + "})\n"
                + "</script>\n"
        );
        return this.getGridHtml();
    }

    private ColumnSettingsObject getColumnSettingsByColumnEntityName(String columnName) {
        for (ColumnSettingsObject columnSettings : columnsSettings) {
            if (columnName.equals(columnSettings.getEntityFieldName())) {
                return columnSettings;
            }
        }
        return null;
    }

    private boolean isColumnAllowed(String columnName) {
        ColumnSettingsObject column = getColumnSettingsByColumnEntityName(columnName);
        if (column != null) {
            return column.isAllowedField();
        }
        return false;
    }

    private void buildFieldsJson(GridCacheObject cache) {
        this.jsonFieldsVariable = "[";
        Integer iteration = 0;
        Integer num = 0;
        boolean added = false;
        for (GridCacheColumnObject column : cache.getColumns()) {

            String search = "false";
            if (column.isAllowed()) {
                if (column.isSearchableColumn()) {
                    if (iteration != 0 && added) {
                        this.jsonFieldsVariable += ",";
                    }
                    search = "true";
                    this.jsonFieldsVariable += "{\"col\":\"" + iteration + "\"," + "\"type\":\"" + column.getType() + "\",\"search\":" + search + ",'filterValue':'','num':'" + num + "'}";
                    added = true;

                }
                num++;
            }
            iteration++;
        }
        this.jsonFieldsVariable += "]";
    }

    private Field[] getEntityAnnotations() throws ClassNotFoundException {
        Class<?> cls = Class.forName(entity.getName());
        Table table = cls.getAnnotation(javax.persistence.Table.class);
        this.table = table.name();
        Field[] fields = cls.getDeclaredFields();
        return fields;
    }

    public GridCacheObject buildCacheObject(Class entity) throws ClassNotFoundException {
        GridCacheObject cacheObject = new GridCacheObject();
        cacheObject.setEntity(entity.getName());
        Field field = null;
        for (ColumnSettingsObject columnSettings : columnsSettings) {
            if ((columnSettings != null)) {
                GridCacheColumnObject cacheColumn = new GridCacheColumnObject();
                cacheColumn.setAllowed(columnSettings.isAllowedField());
                cacheColumn.setColumnName(columnSettings.getEntityFieldName());
                cacheColumn.setType(determineFieldType(columnSettings.getEntityFieldName()));
                cacheColumn.setVisibleName(columnSettings.getTableFieldName());
                cacheColumn.setSearchableColumn(columnSettings.isSearchableField());
                cacheObject.getColumns().add(cacheColumn);
            }
        }
        if (this.columnOptions != null) {
            GridCacheColumnObject cacheColumn = new GridCacheColumnObject();
            cacheColumn.setVisibleName(this.columnOptions.getTableFieldName());
            cacheColumn.setOptionsColumn(true);
            List<GridCacheColumnOption> optionList = new ArrayList<GridCacheColumnOption>();
            for (ColumnOption option : this.columnOptions.getOptions()) {
                GridCacheColumnOption cacheOption = new GridCacheColumnOption();
                cacheOption.setObjectIdSource(option.getObjectIdSource());
                cacheOption.setOptionContent(option.getOptionContent());
                cacheOption.setOptionHref(option.getOptionHref());
                optionList.add(cacheOption);
                cacheColumn.setOptions(optionList);
            }
            cacheColumn.setOptions(optionList);
            cacheObject.getColumns().add(cacheColumn);
        }
        List<GridCacheTableWhereObject> whereList = new ArrayList<GridCacheTableWhereObject>();
        for (TableWhereObject whereObject : tableSettings.getWhereObjects()) {
            String columnName = whereObject.getColumnEntityName();
            String columnType = determineFieldType(columnName);
            String whereType = whereObject.getWhereType();
            String whereValue = whereObject.getWhereVal();
            GridCacheTableWhereObject cacheWhere = new GridCacheTableWhereObject();
            cacheWhere.setColumnName(columnName);
            cacheWhere.setColumnType(columnType);
            cacheWhere.setWhereType(whereType);
            cacheWhere.setWhereVal(whereValue);
            whereList.add(cacheWhere);
        }
        cacheObject.setWheres(whereList);
        return cacheObject;
    }

    public String determineFieldType(String fieldName) {
        Field reflField = null;

        try {
            if (fieldName.contains(".")) {
                String[] innerObjects = fieldName.split("\\.");
                int num = 0;
                Class innerObjectType;
                Class currentEntity = entity;
                for (String innerObject : innerObjects) {
                    if (num < innerObjects.length - 1) {
                        innerObjectType = currentEntity.getDeclaredField(innerObjects[num]).getType();
                        currentEntity = innerObjectType;
                        reflField = innerObjectType.getDeclaredField(innerObjects[num + 1]);
                        num++;
                    }
                }
            } else {
                reflField = entity.getDeclaredField(fieldName);
            }
        } catch (NoSuchFieldException e) {
            System.out.println("field with name " + fieldName + " could not be found in entity " + table);
        }
        String type = "string";
        switch (reflField.getType().getSimpleName()) {
            case "UUID":
                type = "uuid";
                break;
            case "Date":
                type = "date";
                break;
            case "Integer":
            case "double":
                type = "int";
                break;
            case "boolean":
                type = "bool";
                break;
        }
        return type;
    }

    public DataGridBuilder(Class entityClass, List<ColumnSettingsObject> columnSettings, TableSettingsObject tableSettings, ColumnOptionsObject columnOptions, ICacheProvider cacheProvider) throws ClassNotFoundException {
        this.entity = entityClass;
        this.columnsSettings = columnSettings;
        this.tableSettings = tableSettings;
        this.columnOptions = columnOptions;
        this.cacheProvider = cacheProvider;
        Field[] fields = this.getEntityAnnotations();
    }
}
