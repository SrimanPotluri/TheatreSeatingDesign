package theatre;


import java.util.ArrayList;
import java.util.List;

//making this class as a Singleton Class, so that we can create only one instance
// this class creates section and maintains list of sections

public class Theater{

//data members, also includes list of sections
private int no_of_seats;
private int seats_unfilled;
private List<Section> theatre_sections;
private static Theater theatre = null;

//private constructor
private Theater()
{
  theatre_sections = new ArrayList<Section>();
}

//static method to get the instance
public static Theater getInstance()
{
  if(theatre==null)
  {
    theatre = new Theater();
  }
  return theatre;

}
public int getTotalSeats()
{
  return no_of_seats;
}

public int getRemainingSeats()
{
  return seats_unfilled;
}

public void setRemainingSeats(int seats)
{
  seats_unfilled = seats;
}

public void setTotalSeats(int no_of_seats)
{
  this.no_of_seats = no_of_seats;
  this.seats_unfilled = no_of_seats;
}

public void createSection(int rownum, int seats, int section_num)
{
  Section section = new Section(rownum,seats,section_num);
  theatre_sections.add(section);
}


public List<Section> getSectionList()
{
	return theatre_sections;
}

}
