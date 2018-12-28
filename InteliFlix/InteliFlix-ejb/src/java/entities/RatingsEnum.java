

package entities;

/**
 *
 * @author Intern2018
 */
public enum RatingsEnum {
    G("G"),
    PG("PG"),
    PG13("PG-13"),
    R("R"),
    NR("NR"); 
     
    
    private String label;

    RatingsEnum(String ratings) {
        this.label = ratings;
    }
    
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
         System.out.println("Setting is rating");
     
            this.label = label;
        
    }
    
    

    @Override
    public String toString() {
        return label; 
    }
    
}
