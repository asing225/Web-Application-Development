package edu.asu.ser422;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Java class for programmers object.
 * Uses JAXB
 * @author Amanjot Singh
 * */

@XmlRootElement(name="programmers")
public class Programmers
{
  private List<Programmer> programmers;
  
  public Programmers() {}
  
  public List<Programmer> getProgrammers()
  {
    return programmers;
  }
  
  @XmlElement(name="programmer")
  public void setProgrammers(List<Programmer> paramList)
  {
    programmers = paramList;
  }
  
  public void add(Programmer paramProgrammer)
  {
    if (programmers == null) {
      programmers = new ArrayList<Programmer>();
    }
    programmers.add(paramProgrammer);
  }
}