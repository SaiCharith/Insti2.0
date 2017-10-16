package iitbombay.code_catalyst.com.insti20;

/**
 * Created by charith on 16/10/17.
 */

public class searchobjects {
    private String item;
    private String hostel_name;
    private String day;
    private String day_part;

    public searchobjects(String item,String hostel_name,String day,String day_part){
        this.item=item;
        this.hostel_name=hostel_name;
        this.day=day;
        this.day_part=day_part;
    }
    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getHostel_name() {
        return hostel_name;
    }

    public void setHostel_name(String hostel_name) {
        this.hostel_name = hostel_name;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDay_part() {
        return day_part;
    }

    public void setDay_part(String day_part) {
        this.day_part = day_part;
    }
}
