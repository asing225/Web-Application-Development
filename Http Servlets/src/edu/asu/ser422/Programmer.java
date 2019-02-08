package edu.asu.ser422;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="programmer")
@XmlAccessorType(XmlAccessType.FIELD)
public class Programmer
{
  @XmlElement(name="firstname")
  private String firstName;
  @XmlElement(name="lastname")
  private String lastName;
  @XmlElement(name="languages")
  private Language languages;
  @XmlElement(name="days")
  private Days days;
  @XmlElement(name="dreamJob")
  private String dreamJob;
  
  public String getFirstName()
  {
    return firstName;
  }
  
  public void setFirstName(String paramString)
  {
    firstName = paramString;
  }
  
  public String getLastName()
  {
    return lastName;
  }
  
  public void setLastName(String paramString)
  {
    lastName = paramString;
  }
  
  public Language getLanguages()
  {
    return languages;
  }
  
  public void setLanguages(Language paramLanguage)
  {
    languages = paramLanguage;
  }
  
  public Days getDays()
  {
    return days;
  }
  
  public void setDays(Days paramDays)
  {
    days = paramDays;
  }
  
  public String getDreamJob()
  {
    return dreamJob;
  }
  
  public void setDreamJob(String paramString)
  {
    dreamJob = paramString;
  }
  
  public Programmer() {}
  
  public Programmer(String paramString1, String paramString2, Language paramLanguage, Days paramDays, String paramString3)
  {
    days = paramDays;
    dreamJob = paramString3;
    firstName = paramString1;
    lastName = paramString2;
    languages = paramLanguage;
  }
}