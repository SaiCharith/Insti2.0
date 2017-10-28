package iitbombay.code_catalyst.com.insti20;

/**
 * This class is used to hold the fields required to show up in SearchResultActivity
 * @author Code-Catalyst
 *
 */

public class searchobjects {
    /**
     * represents hostel_no
     */
    private int h_no;
    /**
     * represents food item
     */
    private String item;
    /**
     * represents hostel_name
     */
    private String hostel_name;
    /**
     * represents day of the week
     */
    private String day;
    /**
     * represents part of the day.
     */
    private String day_part;

    /**
     * Constructor to initialize all parameters of this class
     * @param h_no
     * @param item
     * @param hostel_name
     * @param day
     * @param day_part
     */
    public searchobjects(int h_no,String item,String hostel_name,String day,String day_part){
        this.h_no=h_no;
        this.item=item;
        this.hostel_name=hostel_name;
        this.day=day;
        this.day_part=day_part;
    }

    /**
     * to get the value of item
     * @return item(String)
     */
    public String getItem() {
        return item;
    }

    /**
     * to set the value of item
     * @param item menu_item
     */
    public void setItem(String item) {
        this.item = item;
    }

    /**
     * to get the hostel_name
     * @return hostel_name
     */
    public String getHostel_name() {
        return hostel_name;
    }

    /**
     * to set the hostel_name
     * @param hostel_name (String)
     */
    public void setHostel_name(String hostel_name) {
        this.hostel_name = hostel_name;
    }

    /**
     * to get the day
     * @return day(String)
     */
    public String getDay() {
        return day;
    }

    /**
     * to set the day
     * @param day (String)
     */
    public void setDay(String day) {
        this.day = day;
    }

    /**
     * to get the part of the day
     * @return day_part (String)
     */
    public String getDay_part() {
        return day_part;
    }

    /**
     * to set the day_part
     * @param day_part
     */
    public void setDay_part(String day_part) {
        this.day_part = day_part;
    }

    /**
     * to get Hostel_no
     * @return h_no (int)
     */
    public int getH_no() {
        return h_no;
    }

    /**
     * to set h_no
     * @param h_no (int)
     */
    public void setH_no(int h_no) {
        this.h_no = h_no;
    }
}
