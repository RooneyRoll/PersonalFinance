/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.personalfinancemanagergrid.classes;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.Column;
import javax.persistence.Table;

/**
 *
 * @author Misho
 */
public class DataGridBuilder {

    private Class entity;
    private List<String> columns = new ArrayList<String>();
    private Map<String, String> allowedFields = new HashMap<String, String>();
    private String table;
    private String jsonFieldsVariable;
    private String columnFilters;
    private String gridHtml = "";

    public String getGridHtml() {
        return gridHtml;
    }

    public void appendToGridHtml(String html) {
        String oldHtml = this.getGridHtml();
        this.gridHtml = (oldHtml + html);
    }

    private void getEntityAnnotations() throws ClassNotFoundException {
        Class<?> cls = Class.forName(entity.getName());
        Table table = cls.getAnnotation(javax.persistence.Table.class);
        this.table = table.name();
        Field[] fields = cls.getDeclaredFields();
        this.jsonFieldsVariable = "[";
        this.columnFilters = "[";
        Integer iteration = 0;
        for (Field field : fields) {
            Column column = field.getAnnotation(javax.persistence.Column.class);
            if (column != null && allowedFields.containsKey(field.getName())) {
                this.columns.add(field.getName());
                if (iteration != 0) {
                    this.jsonFieldsVariable += ",";
                    this.columnFilters += ",";
                }
                this.columnFilters += "{'value':'','type':'" + this.allowedFields.get(field.getName()) + "'}";
                this.jsonFieldsVariable += "{\"name\":\"" + field.getName() + "\"," + "\"type\":\"" + this.allowedFields.get(field.getName()) + "\"}";
                iteration++;
            }
        }
        this.jsonFieldsVariable += "]";
        this.columnFilters += "]";
        System.out.println(columnFilters);
    }

    public String buildHtmlForGrid() {
        this.appendToGridHtml("<table id='grid-" + table + "' class='cell-border stripe' cellspacing='0' width='100%'><thead><tr>");
        String columnsDeclaration = "";
        int columnsLength = this.columns.size();
        int iteration = 0;
        for (String column : columns) {
            this.appendToGridHtml("<th>" + column + "</th>");
            columnsDeclaration += "{'mData':'" + column + "'}";
            if (iteration < columnsLength) {
                columnsDeclaration += ",";
            }
            iteration++;
        }
        this.appendToGridHtml("</tr></thead></table>");
        this.appendToGridHtml("<script type='text/javascript'>\n"
                + "$(document).ready(function(){"
                + "     function buildFilterByType(type,column,title){\n"
                + "         var options = '';\n"
                + "         if(type == 'string'){\n"
                + "		'<option  value=\"eq\">Равно на</option>'+\n"
                + "		options = '<option selected value=\"co\">Съдържа</option>'+\n"
                + "		'<option value=\"st\">Започва с</option>'+\n"
                + "		'<option value=\"en\">Завършва с</option>'\n"
                + "         }\n"
                + "         if(type == 'int'){\n"
                + "		options = '<option value=\"eq\" >Равно на</option>'+\n"
                + "		'<option selected value =\"co\">Съдържа</option>'+\n"
                + "		'<option value=\"st\">Започва с</option>'+\n"
                + "		'<option value=\"en\">Завършва с</option>'+\n"
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
                + "         'aoColumns': [\n"
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
                + "         'ajax': {\n"
                + "             'url': 'gridData',\n"
                + "             'type': 'POST',\n"
                + "             'async': true,\n"
                + "             'method':'post',\n"
                + "             'contentType': 'application/json',\n"
                + "             beforeSend: function (xhr,settings){\n"
                + "                 xhr.setRequestHeader('X-CSRF-TOKEN', token);\n"
                + "             },\n"
                + "             data : function ( d ) {\n"
                + "                 var columns = (d['columns']);\n"
                + "                 var filters = " + this.columnFilters + " \n"
                + "                 $(columns).each(function(key,value){\n"
                + "                     if($('.filter-holder').length > 0){\n"
                + "                         $('.filter-holder').each(function(key,val){\n"
                + "                             var filterVal = $(this).find('option:selected').val();\n"
                + "                             var filterType = $(this).find('option:selected').parent().attr('data-type');\n"
                + "                             var filterObject ={'value':filterVal,'type':filterType};\n"
                + "                             d['columns'][key]['filter'] = filterObject;\n"
                + "                         });\n"
                + "                     }else{\n"
                + "                         d['columns'][key]['filter'] = filters[key];\n"
                + "                     }\n"
                + "                 });\n"
                + "                 d['source'] = '" + entity.getName() + "'\n;"
                + "                 return JSON.stringify(d);\n"
                + "             }\n"
                + "         },"
                + "         'fnInitComplete': function(oSettings, json) {\n"
                + "         $('#grid-" + table + " thead').append('<tr class=\"grid-filters\"></tr>');\n"
                + "         $('#grid-" + table + " th').each( function (key,val) {\n"
                + "             var title = $('#grid-" + table + " th').eq( $(this).index()).text();\n"
                + "             var jsonFields = " + this.jsonFieldsVariable + ";\n"
                + "             var currentField = jsonFields[key];\n"
                + "             var filter = buildFilterByType(currentField.type,key,title);\n"
                + "             $(this).append(filter);"
                + "         });"
                + "         $('.filter-holder input').on( \"keyup change\", function () {"
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
                + "                            \"locale\": \"bg\",\n"
                + "                            \"mode\": \"single\","
                + "                             \"enableTime\":true,\n"
                + "                            \"enableTime\": true\n,"
                + "                            \"wrap\":true\n,"
                + "                             \"dateFormat\": 'Y-m-d h:m:S K'"
                + "                 });\n"
                + "             }\n"
                + "         })\n"
                + "         $('.dataTable thead th').unbind('click.DT');"
                + "         $('.dataTable thead th').click(function(e) {\n"
                + "             var clicked = $(e.target);"
                + "             if (!($(e.target).is('th' ))){ \n"
                + "             } else { \n"
                + "                 var columnIndex = clicked.index();\n"
                + "                 var orderType = 'desc';\n"
                + "                 var sortedBy = clicked.attr('aria-sort');\n"
                + "                 if(typeof sortedBy == 'undefined' || sortedBy == 'ascending'){\n"
                + "                     orderType = 'desc';\n"
                + "                 }else{\n"
                + "                     orderType = 'asc';\n"
                + "                 }\n"
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

    public DataGridBuilder(Class entityClass, Map<String, String> fields) throws ClassNotFoundException {

        this.entity = entityClass;
        this.allowedFields = fields;
        this.getEntityAnnotations();
    }
}
