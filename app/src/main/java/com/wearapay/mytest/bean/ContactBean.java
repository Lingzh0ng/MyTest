package com.wearapay.mytest.bean;

import com.wearapay.mytest.util.Cn2py;

/**
 * Created by lyz on 2016/12/16.
 */
public class ContactBean {
  private String name;
  private String phone;
  private String py;

  public ContactBean(String name, String phone) {
    this.name = name;
    this.phone = phone;
    if ("惡魔Θ天使".equals(name)){
      name = "惡魔天使";
    }
    this.py = Cn2py.converterToFirstSpell(name).toUpperCase();
  }

  @Override public String toString() {
    return "ContactBean{" +
        "name='" + name + '\'' +
        ", phone='" + phone + '\'' +
        ", py='" + py + '\'' +
        '}';
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getPy() {
    return py;
  }

  public void setPy(String py) {
    this.py = py;
  }
}
