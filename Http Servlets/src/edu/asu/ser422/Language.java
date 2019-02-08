package edu.asu.ser422;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="languages")
public class Language
{
  private ArrayList<String> language;
  
  public Language() {}
  
  public ArrayList<String> getLanguage()
  {
    return language;
  }
  
  @XmlElement(name="language")
  public void setLanguage(ArrayList<String> paramArrayList)
  {
    language = paramArrayList;
  }
  
  public void add(String paramString)
  {
    if (language == null) {
      language = new ArrayList<String>();
    }
    language.add(paramString);
  }
}