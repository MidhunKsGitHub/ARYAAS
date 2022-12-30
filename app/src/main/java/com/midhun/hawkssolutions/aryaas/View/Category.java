package com.midhun.hawkssolutions.aryaas.View;

public class Category {

    private String id,name,description,metatitle,metadescription,metakeywords,parent,ancestors,path,image,thumb,is_top_menu,sort_order,status,deleteStatus;

    public Category(String id, String name, String description, String metatitle, String metadescription, String metakeywords, String parent, String ancestors, String path, String image, String thumb, String is_top_menu, String sort_order, String status, String deleteStatus) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.metatitle = metatitle;
        this.metadescription = metadescription;
        this.metakeywords = metakeywords;
        this.parent = parent;
        this.ancestors = ancestors;
        this.path = path;
        this.image = image;
        this.thumb = thumb;
        this.is_top_menu = is_top_menu;
        this.sort_order = sort_order;
        this.status = status;
        this.deleteStatus = deleteStatus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMetatitle() {
        return metatitle;
    }

    public void setMetatitle(String metatitle) {
        this.metatitle = metatitle;
    }

    public String getMetadescription() {
        return metadescription;
    }

    public void setMetadescription(String metadescription) {
        this.metadescription = metadescription;
    }

    public String getMetakeywords() {
        return metakeywords;
    }

    public void setMetakeywords(String metakeywords) {
        this.metakeywords = metakeywords;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getAncestors() {
        return ancestors;
    }

    public void setAncestors(String ancestors) {
        this.ancestors = ancestors;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getIs_top_menu() {
        return is_top_menu;
    }

    public void setIs_top_menu(String is_top_menu) {
        this.is_top_menu = is_top_menu;
    }

    public String getSort_order() {
        return sort_order;
    }

    public void setSort_order(String sort_order) {
        this.sort_order = sort_order;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(String deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

}
