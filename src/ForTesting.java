import java.io.FileWriter;
import java.io.IOException;


/* Just using this for debugging/testing purposes
 * This class can be deleted when project is finished.
 */

public class ForTesting {

	static int nCols = Main.nCols;
	static int nRows = Main.nRows;
	
	public static void testPrintMap(char[][]map) {
		System.out.println();
		String space = "     ";
		System.out.print(space);
		for(int i = 0; i < nCols; i++) {
			if( i < 10) {
				System.out.print(" "+i+"   ");
			}else if ( i < 100) {
				System.out.print(" "+i+"  ");
			}else{
				System.out.print(" "+i+" ");
			}
		}
		System.out.println();
		System.out.print(space);
		for(int i = 0; i < nCols; i++ ) {
			System.out.print(" ____");
		}
		System.out.println();
		
		for(int i = 0; i < nRows; i++) {
			if( i < 10) {
				System.out.print("   "+i+" ");
			}else if ( i < 100) {
				System.out.print("  "+i+" ");
			}else{
				System.out.print(" "+i+" ");
			}
			
			for(int k = 0; k < nCols; k++) {
				if(map[i][k] == '1' || map[i][k] == '2') {
					System.out.print("|    ");
				}else {
					System.out.print("|" + map[i][k] + "   ");	
				}

			}
			System.out.println("|");
			System.out.print(space);
			for(int k = 0; k < nCols; k++) {
				System.out.print("|____");
			}
			System.out.println("|");
		}
	}
	
	public static void testPrintMapToFile(char[][]map, String path) {
	    try {
	        FileWriter writer = new FileWriter(path);
			writer.write("\n");
			String space = "     ";
			writer.write(space);
			for(int i = 0; i < nCols; i++) {
				if( i < 10) {
					writer.write(" "+i+"   ");
				}else if ( i < 100) {
					writer.write(" "+i+"  ");
				}else{
					writer.write(" "+i+" ");
				}
			}
			writer.write("\n");
			writer.write(space);
			for(int i = 0; i < nCols; i++ ) {
				writer.write(" ____");
			}
			writer.write("\n");
			
			for(int i = 0; i < nRows; i++) {
				if( i < 10) {
					writer.write("   "+i+" ");
				}else if ( i < 100) {
					writer.write("  "+i+" ");
				}else{
					writer.write(" "+i+" ");
				}
				
				for(int k = 0; k < nCols; k++) {
					if(map[i][k] == '1' || map[i][k] == '2') {
						writer.write("|    ");
					}else {
						writer.write("|" + map[i][k] + "   ");	
					}

				}
				writer.write("|");
				writer.write("\n");
				writer.write(space);
				for(int k = 0; k < nCols; k++) {
					writer.write("|____");
				}
				writer.write("|");
				writer.write("\n");
			}
	        
	        
	        
	        
	        writer.close();
	      } catch (IOException e) {
	        System.out.println("An error occurred.");
	        e.printStackTrace();
	      }
	}

}
