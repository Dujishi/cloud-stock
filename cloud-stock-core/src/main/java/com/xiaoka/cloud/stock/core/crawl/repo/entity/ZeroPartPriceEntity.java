package com.xiaoka.cloud.stock.core.crawl.repo.entity;


import java.util.Date;

/**
 *
 * for car brand
 * ZeroPartPriceEntity
 *
 * @author zhouze
 */
public class ZeroPartPriceEntity {
	
    private Integer id;
    private String  brand;
    private String  pid;
    private String  channel;
    private String  factoryType;
    private String  costPrice;
    private String  eotPrice;
    private String  price;
    private String  partType;
    private String  mill;
    private String  location;
    private String  amount;
    private String  origin;
    private String  supplier;
    private Integer isValid;
    private Date    createTime;
    private String  createBy;
    private Date    updateTime;
    private String  updateBy;
    private String  remark;

	    public Integer getId()
    {
        return this.id;
    }
    	    public String getBrand()
    {
        return this.brand;
    }
    	    public String getPid()
    {
        return this.pid;
    }
    	    public String getChannel()
    {
        return this.channel;
    }
    	    public String getFactoryType()
    {
        return this.factoryType;
    }
    	    public String getCostPrice()
    {
        return this.costPrice;
    }
    	    public String getEotPrice()
    {
        return this.eotPrice;
    }
    	    public String getPrice()
    {
        return this.price;
    }
    	    public String getPartType()
    {
        return this.partType;
    }
    	    public String getMill()
    {
        return this.mill;
    }
    	    public String getLocation()
    {
        return this.location;
    }
    	    public String getAmount()
    {
        return this.amount;
    }
    	    public String getOrigin()
    {
        return this.origin;
    }
    	    public String getSupplier()
    {
        return this.supplier;
    }
    	    public Integer getIsValid()
    {
        return this.isValid;
    }
    	    public Date getCreateTime()
    {
        return this.createTime;
    }
    	    public String getCreateBy()
    {
        return this.createBy;
    }
    	    public Date getUpdateTime()
    {
        return this.updateTime;
    }
    	    public String getUpdateBy()
    {
        return this.updateBy;
    }
    	    public String getRemark()
    {
        return this.remark;
    }
    

    public void setId(Integer id)
    {
                this.id = id;
            }  
    public void setBrand(String brand)
    {
                this.brand = brand == null ? null :  brand.trim();
            }  
    public void setPid(String pid)
    {
                this.pid = pid == null ? null :  pid.trim();
            }  
    public void setChannel(String channel)
    {
                this.channel = channel == null ? null :  channel.trim();
            }  
    public void setFactoryType(String factoryType)
    {
                this.factoryType = factoryType == null ? null :  factoryType.trim();
            }  
    public void setCostPrice(String costPrice)
    {
                this.costPrice = costPrice == null ? null :  costPrice.trim();
            }  
    public void setEotPrice(String eotPrice)
    {
                this.eotPrice = eotPrice == null ? null :  eotPrice.trim();
            }  
    public void setPrice(String price)
    {
                this.price = price == null ? null :  price.trim();
            }  
    public void setPartType(String partType)
    {
                this.partType = partType == null ? null :  partType.trim();
            }  
    public void setMill(String mill)
    {
                this.mill = mill == null ? null :  mill.trim();
            }  
    public void setLocation(String location)
    {
                this.location = location == null ? null :  location.trim();
            }  
    public void setAmount(String amount)
    {
                this.amount = amount == null ? null :  amount.trim();
            }  
    public void setOrigin(String origin)
    {
                this.origin = origin == null ? null :  origin.trim();
            }  
    public void setSupplier(String supplier)
    {
                this.supplier = supplier == null ? null :  supplier.trim();
            }  
    public void setIsValid(Integer isValid)
    {
                this.isValid = isValid;
            }  
    public void setCreateTime(Date createTime)
    {
                this.createTime = createTime == null ? null :  (Date) createTime.clone();
            }  
    public void setCreateBy(String createBy)
    {
                this.createBy = createBy == null ? null :  createBy.trim();
            }  
    public void setUpdateTime(Date updateTime)
    {
                this.updateTime = updateTime == null ? null :  (Date) updateTime.clone();
            }  
    public void setUpdateBy(String updateBy)
    {
                this.updateBy = updateBy == null ? null :  updateBy.trim();
            }  
    public void setRemark(String remark)
    {
                this.remark = remark == null ? null :  remark.trim();
            }  

}