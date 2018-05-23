import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		
		//Je r�cup�re un objet "Class" de la classe "ClassWithAnnotation"
		Class<?> classWithAnnotation;
		//Aller un p'tit rappel
			//M�thode 1 : Acc�s par la classe
		classWithAnnotation = ClassWithAnnotation.class;
			//M�thode 2 : Acc�s via une instance de la class
		ClassWithAnnotation objetWithAnnotation = new ClassWithAnnotation();
		classWithAnnotation =  objetWithAnnotation.getClass();
		//On r�cup�re la liste des m�thodes de l'objet "classWithAnnotation" issu de la classe
		Method[] methods = classWithAnnotation.getDeclaredMethods();
		
		//Je parcours les m�thodes pr�sente dans la classe
		for(Method m : methods) {		
			
			//J� cr�er un tableau d'objet "Annotation" � partir de la liste des annoations apport�es � la m�thode
			Annotation[] annonations = m.getAnnotations();
			
			System.out.println("* La m�thode "+m.getName()+" poss�de :"+annonations.length+" annotations !");
			
			//On parcours le tableau d'annotations de la m�thode
			for(Annotation annotation : annonations) {
				//J'affiche le nom de l'interface de mon annotation
				System.out.println("-- Je suis une annotation et je m'appelle : "+annotation.getClass().getInterfaces()[0].getName());
				System.out.println("-- je suis de type : "+annotation.annotationType());
				//Je vais utiliser les infos sp�cifiques de mes annotations				
				if (annotation instanceof MonAnnotation) {					
					
					//On caste l'annotation du type g�n�rique "Annotation" vers le type plus sp�cifique "MonAnnotation"
						//Les 2 variables "sameAnnotation" et "annotation" d�signent le m�me objet 
					MonAnnotation sameAnnotation = (MonAnnotation) annotation;
					//J'affiche les attributs name, lib et run de mon annotation
					System.out.println("---name	:	"+sameAnnotation.name());
					System.out.println("---lib	:	"+sameAnnotation.lib());
					System.out.println("---run	:	"+sameAnnotation.run());
				}				
			}			
		}	
		
		/* 2 - Ex�cution dynamique */
		Scanner sc = new Scanner(System.in);
		boolean quit = false;
		//On lancer � la main
		objetWithAnnotation.methode1();
		objetWithAnnotation.methode2();
		
		while(!quit) {
						
			System.out.println("Faites votres choix :");
			for(int i = 0 ; i < methods.length ; i++) {
				System.out.println((i+1)+") "+methods[i].getName());
			}
				
			//Dynamiquement
			int methodeId = sc.nextInt();
			if(methodeId == 0) {
				quit = true; 
			}else {
				runMethode(objetWithAnnotation,methods[methodeId-1].getName());
			}
		}
		sc.close();
		
	}
	
	
	public static void runMethode(Object obj, String methode) {
		
		Class<?> classeAInstancier = obj.getClass();
		Class<?>[] types = new Class[] {};				
		try {
			Method methodeToRun = classeAInstancier.getMethod(methode,types);				
			
			MonAnnotation annonation = methodeToRun.getAnnotation(MonAnnotation.class);
			if(annonation.run() == true) {
				methodeToRun.invoke(obj, null);
			}else {
				System.err.println("h�las je n'ai pas le droit de lancer la m�thode : "+methodeToRun.getName());
			}			
			
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

}
