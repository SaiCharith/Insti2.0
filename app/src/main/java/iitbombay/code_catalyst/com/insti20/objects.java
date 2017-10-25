package iitbombay.code_catalyst.com.insti20;

/**
 * @author Code-Catalyst
 * This class is used to hold information about a hostel.
 */


class objects {
    /**
     * Stores Hostel name
     */
    String Hostel_name;
    /**
     * Stores hostel number.
     */
    int hostel_no;
    /**
     * Stores current rating given to that hostel
     */
    float rating;
    /**
     * Stores number of likes
     */
    int likes;
    /**
     * Stores number of dislikes
     */
    int dislikes;
    /**
     * Stores image_id of a pirticular hostel.
     */
    int image_id;

    /**
     * Costructor to initialize with hostel number.
     * @param i hostel number
     */
    objects(int i){hostel_no=i;}

}
