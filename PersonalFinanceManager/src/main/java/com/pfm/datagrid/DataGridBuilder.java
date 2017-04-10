/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.datagrid;

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
    private String gridHtml = "";

    public Map<String, String> getAllowedFields() {
        return allowedFields;
    }

    public void setAllowedFields(Map<String, String> allowedFields) {
        this.allowedFields = allowedFields;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public List<String> getColumns() {
        return columns;
    }

    public void setColumns(List<String> columns) {
        this.columns = columns;
    }

    public void addColumn(String column) {
        this.getColumns().add(column);
    }

    public Class getEntity() {
        return entity;
    }

    public void setEntity(Class entity) {
        this.entity = entity;
    }

    public String getGridHtml() {
        return gridHtml;
    }

    public void setGridHtml(String gridHtml) {
        this.gridHtml = gridHtml;
    }

    public void appendToGridHtml(String html) {
        String oldHtml = this.getGridHtml();
        this.setGridHtml(oldHtml + html);
    }

    private void getEntityAnnotations() throws ClassNotFoundException {
        Class<?> cls = Class.forName(getEntity().getName());
        Table table = cls.getAnnotation(javax.persistence.Table.class);
        this.setTable(table.name());
        Field[] fields = cls.getDeclaredFields();
        this.jsonFieldsVariable = "[";
        Integer iteration = 0;
        for (Field field : fields) {
            Column column = field.getAnnotation(javax.persistence.Column.class);
            if (column != null && this.getAllowedFields().containsKey(field.getName())) {
                this.addColumn(field.getName());
                if (iteration != 0) {
                    this.jsonFieldsVariable += ",";
                }
                this.jsonFieldsVariable += "{\"name\":\"" + field.getName() + "\"," + "\"type\":\"" + this.getAllowedFields().get(field.getName()) + "\"}";
                iteration++;
            }
        }
        this.jsonFieldsVariable += "]";
        System.out.println(this.jsonFieldsVariable);
    }

    public String buildHtmlForGrid() {
        this.appendToGridHtml("<table id='grid-" + this.getTable() + "' class='cell-border stripe' cellspacing='0' width='100%'><thead><tr>");
        String columnsDeclaration = "";
        String data = "{}";
        int columnsLength = this.getColumns().size();
        int iteration = 0;
        for (String column : this.getColumns()) {
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
                + "function buildFilterByType(type,column,title){\n"
                + "	var options = \"\";\n"
                + "	if(type == \"string\"){\n"
                + "		options = \"<option value='co'>Съдържа</option>\"+\n"
                + "		\"<option selected value='eq'>Равно на</option>\"+\n"
                + "		\"<option value='st'>Започва с</option>\"+\n"
                + "		\"<option value='en'>Завършва с</option>\"\n"
                + "	}\n"
                + "	if(type == \"int\"){\n"
                + "		options = \"<option value='eq' selected>Равно на</option>\"+\n"
                + "		\"<option value ='co'>Съдържа</option>\"+\n"
                + "		\"<option value='st'>Започва с</option>\"+\n"
                + "		\"<option value='en'>Завършва с</option>\"+\n"
                + "		\"<option value='lt'>По-малко от</option>\"+\n"
                + "		\"<option value='gt'>По-голямо от</option>\"\n"
                + "	}\n"
                + "	return \"<div class='filter-holder filter-size-" + this.getAllowedFields().size() + "'>\"+\n"
                + "		\"<div class='filter-title'>\"+title+\"</div>\"+\n"
                + "		\"<div class='filter-type'>\"+\n"
                + "		\"<select data-column='\"+column+\"' data-type='\"+type+\"' class='filter-type-select'>\"+\n"
                + "		options+\n"
                + "		\"</select>\"+\n"
                + "		\"</div><div class='filter-input'>\"+\n"
                + "		\"<input data-column='\"+column+\"' type='text' placeholder='Търсене по \"+title+\"' />\"+\n"
                + "		\"</div></div>\";\n"
                + "}"
                + "var token = $(\"meta[name='_csrf']\").attr(\"content\"); \n"
                + "var header = $(\"meta[name='_csrf_header']\").attr(\"content\");\n"
                + "var table = $('#grid-" + this.getTable() + "').DataTable({\n"
                + "'processing': true,\n"
                + "responsive: true,\n"
                + "'serverSide': true,\n"
                + "\"paging\": true,\n"
                + "'ajax': {\n"
                + "     \"url\": \"gridData\",\n"
                + "     \"type\": \"POST\",\n"
                + "     \"async\": true,\n"
                + "     \"method\":\"post\",\n"
                + "     \"contentType\": \"application/json\",\n"
                + "beforeSend: function (xhr,settings){\n"
                + "     xhr.setRequestHeader(\"X-CSRF-TOKEN\", token);\n"
                + "},\n"
                + "data : function ( d ) {\n"
                + "     d[\"filter\"]=[];"
                + "         $('.filter-holder').each(function(key,val){\n"
                + "             var filterVal = $(this).find('option:selected').val();\n"
                + "             var filterType = $(this).find('option:selected').parent().attr('data-type');"
                + "             var filterObject ={'value':filterVal,'type':filterType};"
                + "             d[\"filter\"][key] = filterObject;\n"
                + "         });                  "
                + "     d[\"source\"] = \"" + this.getEntity().getName() + "\"\n;"
                + "         return JSON.stringify(d);\n"
                + "     }\n"
                + "},\n"
                + "'aaData': " + data + ",\n"
                + "'aoColumns': [\n"
                + columnsDeclaration
                + "]\n"
                + "})\n"
                + "var filtersClass = 'grid-" + this.getTable() + "-filters';        \n"
                + "$('#grid-" + this.getTable() + "').before('<div class=\"grid-filters\" id=\"grid-" + this.getTable() + "-filters\"></div>');\n"
                + "var filtersContainer = $('#grid-" + this.getTable() + "-filters');\n"
                + "var fieldsArray = {};"
                + "$('#grid-" + this.getTable() + " th').each( function (key,val) {\n"
                + "         var title = $('#grid-" + this.getTable() + " th').eq( $(this).index()).text();\n"
                + "         var jsonFields = " + this.jsonFieldsVariable + ";"
                + "         var currentField = jsonFields[key];"
                + "         var filter = buildFilterByType(currentField.type,key,title);"
                + "         filtersContainer.append(filter);\n"
                + "});"
                + "$(\".filter-holder input\").on( 'keyup change', function () {\n"
                + "     table \n"
                + "         .column($(this).attr('data-column'))\n"
                + "         .search($(this).val())\n"
                + "         .draw();\n"
                + "});"
                + "$('.grid-filters select').each(function(){\n"
                + "     $(this).select2();"
                + "$('.filter-type-select').on(\"change\", function(e) {\n"
                        + "table \n"
                + "         .column($(this).attr('data-column'))\n"
                + "         .search($(this).parent().parent().find('input').val())\n"
                + "         .draw();\n"
                + "     });\n"
                + "});\n"
                + "})\n"
                + "</script>\n"
        );
        return this.getGridHtml();
    }

    public DataGridBuilder(Class entityClass, Map<String, String> fields) throws ClassNotFoundException {
        this.setEntity(entityClass);
        this.setAllowedFields(fields);
        this.getEntityAnnotations();
    }

}
