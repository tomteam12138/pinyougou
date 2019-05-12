package cn.itcast.core.pojo.item;

import java.io.Serializable;

public class ItemCatNew implements Serializable {

    private Long id;
    private Long parentId;
    private String name;
    private String isDelete;
    private String itemStatus;
    private Long typeId;

    public ItemCatNew() {
    }

    public ItemCatNew(Long id, Long parentId, String name, String isDelete, String itemStatus, Long typeId) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
        this.isDelete = isDelete;
        this.itemStatus = itemStatus;
        this.typeId = typeId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    public String getItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(String itemStatus) {
        this.itemStatus = itemStatus;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    @Override
    public String toString() {
        return "ItemCatNew{" +
                "id=" + id +
                ", parentId=" + parentId +
                ", name='" + name + '\'' +
                ", isDelete='" + isDelete + '\'' +
                ", itemStatus='" + itemStatus + '\'' +
                ", typeId=" + typeId +
                '}';
    }
}
