package theatre;


public class Section implements Comparable<Section>{

//data members
private int rownum;
private int seats_per_section;
private int section_num;
private int remaining_seats_per_section;

//default constructor
public Section()
{}

//parameterized constructor
public Section(int rownum, int seats_per_section, int section_num)
{
    this.rownum = rownum;
    this.seats_per_section = seats_per_section;
    this.section_num = section_num;
    this.remaining_seats_per_section = seats_per_section;
}

public int getRowNum()
{
    return rownum;
}

public int getSeatsPerSection()
{
    return seats_per_section;
}

public void setSeatsPerSection(int updatedseats)
{
    seats_per_section = updatedseats;
}

public int getSectionNum()
{
	return section_num;
}

public int getRemainingSeats()
{
	return remaining_seats_per_section;
}

public void setRemainingSeats(int remaining_seats)
{
	remaining_seats_per_section = remaining_seats;
}

@Override
public int compareTo(Section o) {
	// TODO Auto-generated method stub
	
	int result = 0;
    if((this.getSeatsPerSection() - o.getSeatsPerSection()) == 0){
        
        if((this.getRowNum() - o.getRowNum()) == 0){
            
            result = this.getSectionNum() - o.getSectionNum();
            
        }else{
            
            result = this.getRowNum() - o.getRowNum();
            
        }
        
    }else{
        
        result = this.getRemainingSeats() - o.getRemainingSeats();
        
    }
    
    return result;
}

}
