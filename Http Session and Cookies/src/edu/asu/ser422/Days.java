package edu.asu.ser422;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Java class for Days object
 * Uses JAXB
 * @author Amanjot Singh
 * */

@XmlRootElement(name="days")
public class Days
{
  private ArrayList<String> days;
  
  public Days() {}
  
  public ArrayList<String> getDay()
  {
    return days;
  }
  
  @XmlElement(name="day")
  public void setDay(ArrayList<String> paramArrayList)
  {
    days = paramArrayList;
  }
  
  public void add(String paramString)
  {
    if (days == null) {
      days = new ArrayList<String>();
    }
    days.add(paramString);
  }
}