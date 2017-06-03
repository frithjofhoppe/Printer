package printlearn;

import java.awt.print.PrinterException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.print.event.PrintJobAdapter;
import javax.print.event.PrintJobEvent;

import org.apache.pdfbox.PrintPDF;
//import org.apache.pdfbox.pdmodel.font.PDVectorFont;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
//import org.apache.pdfbox.pdmodel.font.PDCIDFontType2;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
//import org.apache.pdfbox.printing.PDFPrinter;
import javafx.scene.Node;
import javafx.scene.shape.Circle;

class Printer {

	final public String savePath = "D:/karteikarten.pdf";
	static int fontSize = 12;
	public ArrayList<PDPage> pdfPages = new ArrayList<PDPage>();
	public static ArrayList<FileInputStream> files = new ArrayList<FileInputStream>();
	public static ArrayList<String[]> quantity = new ArrayList<String[]>();
	public static float POINTS_PER_INCH = 72;
	public static float POINTS_PER_MM = 1 / (10 * 2.54f) * POINTS_PER_INCH;
	public static int sites = 0;

	public static void main(String[] args) throws Exception {

		createPDF();
	}

	public static void createPDF() throws COSVisitorException, IOException, PrinterException {
		File file  = new File("D:/karteikarten.pdf");
		PDDocument doc = PDDocument.load(file);
		
		setVariables();
		createPage(doc, sites);
		// createQuantitySpecificPage(doc, size);
		//setText(quantity.size(), doc);
		//setRaster(doc);
		versuch();
		saveDocument(doc);
		//printDocument(doc);
		
	}

	public static void printDocument(PDDocument d) throws PrinterException {
		d.print();
	}
	
	public static void versuch()
	{
		 try{
        
        System.out.println("Create Simple PDF file with Text");
        String fileName = "d:/simp4.pdf"; // name of our file
        
        PDDocument doc = new PDDocument();
        


        for(int i = 0; i < 5;i++)
        {
        	doc.addPage(new PDPage());
        }

        PDPageContentStream content = new PDPageContentStream(doc, (PDPage) doc.getDocumentCatalog().getAllPages().get(2));
        
        content.beginText();
        content.setFont(PDType1Font.HELVETICA, 26);
        content.moveTextPositionByAmount(220, 750);
        content.drawString("Registration Form");
        content.endText();
        
        
        content.beginText();
        content.setFont(PDType1Font.HELVETICA, 16);
        content.moveTextPositionByAmount(80, 700);
        content.drawString("Name : ");
        content.endText();
        
        
        content.beginText();
        content.setFont(PDType1Font.HELVETICA, 16);
        content.moveTextPositionByAmount(80,650);
        content.drawString("Father Name : ");
        content.endText();
        
        content.beginText();
        content.moveTextPositionByAmount(80,600);
        content.drawString("DOB : ");
        content.endText();
        
        content.drawLine(74,0,74,POINTS_PER_MM*148);
        
        content.close();
        doc.save(fileName);
        doc.close();
        
        System.out.println("your file created in : "+ System.getProperty("user.dir"));

        }
        catch(IOException | COSVisitorException e){
        
        System.out.println(e.getMessage());
        
        }

	}

	public static void setRaster(PDDocument doc) throws IOException {
		float x = 0;
		float y = 0;
		PDDocumentCatalog docCatalog = doc.getDocumentCatalog();

		for (int i = 0; i < sites + 1; i++) {
			y = (float) (52.5 * POINTS_PER_MM);
			x = 0;
			PDPageContentStream newContentStream = new PDPageContentStream(doc,
					(PDPage) docCatalog.getAllPages().get(i), true, false, false);
			for (int o = 0; o < 3; o++) // horizontal
			{
				newContentStream.addLine(x, y, (float) (POINTS_PER_MM * 297), y);
				System.out.print(x + " " + y + " " + (float) (POINTS_PER_MM * 297) + " " + y);
				y += (POINTS_PER_MM * 52.5);
			}

			y = 0;
			x = (float) (74.25 * POINTS_PER_MM);

			for (int a = 0; a < 3; a++) // vertikal
			{
				newContentStream.addLine(x, y, x, (float) (POINTS_PER_MM * 210));
				x += (POINTS_PER_MM * 74.25);
			}
			newContentStream.close();
		}
	}

	public static void createPage(PDDocument d, int value) {
		for (int i = 0; i < value + 1; i++) {
			d.addPage(new PDPage(new PDRectangle(297 * POINTS_PER_MM, 210 * POINTS_PER_MM)));
		}
	}

	public static void saveDocument(PDDocument d) throws COSVisitorException, IOException {
		d.save("d:\\karteikarten.pdf");
		files.add(new FileInputStream("D:/karteikarten.pdf"));
	}

	public static void createQuantitySpecificPage(PDDocument doc, ArrayList<String[]> list) {
		createPage(doc, list.size());
	}

	public static void setVariables() {
		String[] test = { "nouveau", "neu" };
		String[] test2 = { "vieille", "alt" };

		quantity.add(test2);
		quantity.add(test);

		sites = (quantity.size() / 16);
		sites += 1;

		System.out.print(sites + " SEiten");
	}

	public static void setText(int all, PDDocument doc) throws IOException {
		PDDocumentCatalog docCatalog = doc.getDocumentCatalog();

		PDFont font = PDType1Font.HELVETICA_OBLIQUE;
		ArrayList<String[]> cache = new ArrayList<String[]>();

		System.out.print(cache.size() + " CacheSize");
		
		float x = 0;
		float y = 0;

		System.out.println(sites + " seiten");

		for (int o = 0; o < sites; o++) {
			
			cache = getData();
			x = 0;
			y = 0;
			
			System.out.println(cache.size()+ " cache text");
			PDPageContentStream contentStream = new PDPageContentStream(doc,(PDPage) docCatalog.getAllPages().get(o), true, false, false);
			for (int a = 0; a < cache.size(); a++) {
				for (int i = 1; i < cache.size() + 1; i++) {
					setFirstText(cache.get(i - 1), contentStream, font, x, y);
					x += (POINTS_PER_MM * 60.25);	
				}
				x = 0;
				y += (POINTS_PER_MM * 52.25);
			}

			x = 594 * POINTS_PER_MM;
			y = 0;

			for (int a = 0; a < cache.size(); a++) {
				for (int i = 1; i < cache.size() + 1; i++) {
					setSecondText(cache.get(i - 1), contentStream, font, x, y);
					x += (POINTS_PER_MM * 60.25);
				}
				x = 0;
				y += (POINTS_PER_MM * 52.25);
			}

		}

	}

	public static void setFirstText(String[] strings, PDPageContentStream contentStream, PDFont font, float x, float y)
			throws IOException {
		contentStream.beginText();
		contentStream.setFont(font, fontSize);
		contentStream.moveTextPositionByAmount(x, y);
		contentStream.drawString(strings[0]);
		contentStream.endText();

	}

	private static ArrayList<String[]> getData() {
		ArrayList<String[]> back = new ArrayList<String[]>();
		int size = 0;

		System.out.print(quantity.size() + "quantity size$$$$");
		
		if (quantity.size() < 16) {
			size = quantity.size();
		} else {
			size = 16;
		}

		for (int i = 0; i < size; i++) {
			back.add(quantity.get(i));
		}

		return back;

	}

	public static void setSecondText(String[] strings, PDPageContentStream contentStream, PDFont font, float x, float y)
			throws IOException {
		contentStream.beginText();
		contentStream.setFont(font, fontSize);
		contentStream.moveTextPositionByAmount(x, y);
		contentStream.drawString(strings[1]);
		contentStream.endText();

	}

	public void createPAgeLAyout() {
		/*
		 * float POINTS_PER_INCH = 72; float POINTS_PER_MM = 1 / (10 * 2.54f) *
		 * POINTS_PER_INCH; new PDPage(new PDRectangle(297 * POINTS_PER_MM, 210
		 * * POINTS_PER_MM));
		 * 
		 * PDPage page = new PDPage(PDRectangle.A4); page.setRotation(90);
		 * doc.addPage(page); PDRectangle pageSize = page.getMediaBox(); float
		 * pageWidth = pageSize.getWidth(); PDPageContentStream contentStream =
		 * new PDPageContentStream(doc, page, AppendMode.OVERWRITE, false);
		 * 
		 * 
		 * PDPage page = new PDPage(new PDRectangle(PDRectangle.A4.getHeight(),
		 * PDRectangle.A4.getWidth()));
		 */
	}

}