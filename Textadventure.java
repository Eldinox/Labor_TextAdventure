/**
 * Created by Markus Alpers on 20.10.2016.
 */

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Textadventure extends Application 
{
    private int aktuellerAbschnitt;
    private String[] abschnitte;
    private String[][] beschriftungen;
    private int[][] naechsterAbschnitt;
    private Text beschreibung;
    private Button button1;
    private Button button2;

    @Override
    public void start(Stage primaryStage) 
    {
        primaryStage.setTitle("Textadventure");

        int abenteuerlaenge = 10;                           //Maximale Anzahl der verschiedenen Auswahlbildschirme
        aktuellerAbschnitt = 0;                             //Gibt an in welchem Abschnitt man sich befindet

        abschnitte = new String[abenteuerlaenge];           //Array das mit den Statusmitteilungen gefüllt wird
        beschriftungen = new String[abenteuerlaenge][2];    //Beschriftung der Buttons (10x2 Beschriftungen)
        naechsterAbschnitt = new int[abenteuerlaenge][2];

        //Die verschiedenen Abschnitte
        // 0: Startpunkt
        abschnitte[0] = "Du erwachst. Es ist kalt und du hast einen Kater. Es riecht ein wenig muffig.";
        beschriftungen[0][0] = "Du siehst dich um.";
        naechsterAbschnitt[0][0] = 1;
        beschriftungen[0][1] = "Du stehst auf.";
        naechsterAbschnitt[0][1] = 2;

        // 1: Umsehen nach erfwachen
        abschnitte[1] = "Ich glaub, du stehst im Wald... nein, vielmehr liegst du im Wald. Muss eine üble Party gewesen sein.";
        beschriftungen[1][0] = "Du stehst auf.";//1.a
        naechsterAbschnitt[1][0] = 2;
        beschriftungen[1][1] = "Du schläfst lieber nochmal ne Runde.";//1.b
        naechsterAbschnitt[1][1] = 0;

        // 1.a: Du weißt nicht was du tun sollst -> a)Du greifst in deine Taschen b)Du siehst auf deine Uhr
        // 2.a: Du findest dein Handy. Du hast keinen Empfang aber siehst, es ist Montag 7:28. In einer Stunde beginnt deine Vorlesung
        // 2.b: Die Zahlen auf deiner Uhr sagen dir, dass du nurnoch eine Stunde hast bis deine erste Vorlesung beginnt
        // 1.b: 'Das gleiche wie bei 1.a, nur dass du bei 2. 30 Minuten weniger Zeit hast'

        // 2: Aufstehen
        abschnitte[2] = "Schwankend kommst du auf die Füße. Der nächste Wald von deiner WG aus liegt in nördlicher Richtung. Wie war das doch noch?";
        beschriftungen[2][0] = "An Bäumen wächst das Moos auf der Südseite, denn Moos wächst im Sonnenlicht.";
        naechsterAbschnitt[2][0] = 3;
        beschriftungen[2][1] = "Quatsch, es ist genau umgekehrt.";
        naechsterAbschnitt[2][1] = 4;

        //2.2: Weg
        //Weg auf dem Boden. Solltest du ihm folgen? -> a)Weg heißt Zivilisation. Folgen! b)Kenne den Weg nicht. Nicht folgen!
        //3.a: Du bemerkst ein rostiges Schild am Wegrand. Wald den du kennst aber vielleicht falsche Seite?
        //                                           -> a)Du kennst hier nichts. Umdrehen! b)Wird schon stimmen. Weiter!

        // 3: Nach Norden  -- Überflüssig
        abschnitte[3] = "So soll es sein... Du schwankst durch einen unbekannten Wald in eine Richtung, die du für Süden hältst.";
        beschriftungen[3][0] = "Du bleibst bei deiner Entscheidung und gehst immer weiter in die Richtung.";
        naechsterAbschnitt[3][0] = 5;
        beschriftungen[3][1] = "Du bist dir nicht sicher und drehst nochmal um.";
        naechsterAbschnitt[3][1] = 6;

        //4.2 Abhang
        //Du gelangst an einen Abhang. Ist das etwa das Stück Wald, dass du aus deinem Fenster sehen kannst? Dann ist deine WG direkt vor dir
        // -> a)Wag den Sprung, wenn das der bekannte Abhang ist, ist er nichtmal einen halben Meter hoch. b)Im dunkeln hier runterspringen? lieber einen Weg suchen auch wenn wenig Zeit
        //4.a Du bist tot. Wer springt auch im dunkeln von einer Klippe?...
        //4.b Nach einigen Minuten findest du endlich einen Weg nach unten. Es war um einiges tiefer als gedacht. -> a)umdrehen? b)weiter?
        //4.b.b Dir kommt nichts mehr bekannt vor. Du hast dich komplett verlaufen 

        // 4: Nach Süden
        abschnitte[4] = "So soll es sein... Du schwankst durch einen unbekannten Wald in eine Richtung, die du für Süden hältst.";
        beschriftungen[4][0] = "Du bleibst bei deiner Entscheidung und gehst immer weiter in die Richtung.";
        naechsterAbschnitt[4][0] = 9;
        beschriftungen[4][1] = "Du bist dir nicht sicher und drehst nochmal um.";
        naechsterAbschnitt[4][1] = 5;

        //4.3 Mädchen
        //Nach einiger Zeit siehst du ein Mädchen am Wegrand stehen. Sie trägt bei dieser Kälte bloß eine dünne Jacke mit Kapuze. Nichtmal Schuhe
        // -> a)Geh schleunigst weiter. Du hast keine Zeit b)Du kannst sie nicht einfach stehenlassen. Frag ob alles in Ordnung ist
        //5.b Sie sagt sie bräuchte dringen Geld. Wirst du ihr helfen? -> a)ja b)nein
        //5.b.a Wie viel wirst du ihr geben? -> a)Gib ihr wenig b)Gib ihr was du hast
        //5.2 Sie scheint glücklich und fragt was du zu so später Stunde hier machst. Du erzählst ihr was passiert ist und sie bietet ihre Hilfe an
        // -> a)alleine weitergehen b)ihr folgen
        //5.2.b Du folgst ihr eine ganze Weile, doch du hast das Gefühl ihr geht immer tiefer in den Wald.
        // -> a)folge ihr still b)sag lieber du willst doch den anderen Weg gehen und dreh um
        //5.3 Wenn wenig Geld gegeben -> a) wenn viel Geld gegeben -> b)
        //5.3.a Ihr kommt an eine kleine Hütte mitten im Wald. Das Mädchen tötet dich
        //5.3.b Ihr kommt an eine Wegkreuzung am Waldrand und das Mädchen deutet auf ein Gebäude in einiger Entfernung. Tatsächlich ist dies deine WG und deine Uni gleich daneben. Geschafft
        

        // 5: Weiter nach Norden
        abschnitte[5] = "Und du gehst ... und gehst ... und gehst...";
        beschriftungen[5][0] = "und drehst dann irgendwann um, weils so weit doch nicht sein kann.";
        naechsterAbschnitt[5][0] = 6;
        beschriftungen[5][1] = "und gehst immer weiter.";
        naechsterAbschnitt[5][1] = 8;

        // 6: Doch nach Süden
        abschnitte[6] = "Du kommst an der Stelle vorbei, an der du aufgewacht bist.";
        beschriftungen[6][0] = "Weiter geht's.";
        naechsterAbschnitt[6][0] = 9;
        beschriftungen[6][1] = "Nein, doch lieber umdrehen.";
        naechsterAbschnitt[6][1] = 5;

        //
        abschnitte[7] = "Testeintrag...";
        beschriftungen[7][0] = "Neustart";
        naechsterAbschnitt[7][0] = 0;
        beschriftungen[7][1] = "Neustart";
        naechsterAbschnitt[7][1] = 2;

        // 8: Game Over
        abschnitte[8] = "Du hast dich hoffnungslos verirrt.";
        beschriftungen[8][0] = "Nochmal von vorne.";
        naechsterAbschnitt[8][0] = 0;
        beschriftungen[8][1] = "Nochmal von vorne.";
        naechsterAbschnitt[8][1] = 0;

        // 9: Ende
        abschnitte[9] = "Nach fünf Minuten brichst du durch die Hecke auf das Gelände deines Wohnheims. Dann mal ran an Statistik 1 - Und mögest du alle Hoffnung verlieren.";
        beschriftungen[9][0] = "Nochmal von vorne.";
        naechsterAbschnitt[9][0] = 0;
        beschriftungen[9][1] = "Nochmal von vorne.";
        naechsterAbschnitt[9][1] = 0;

        GridPane grid = new GridPane();                     //Grid in dem die Elemente angeordnet werden
        grid.setAlignment(Pos.CENTER);                      //Ankerpunkt des Grids
        grid.setHgap(10);
        grid.setVgap(10);                                   //Höhe und Breite der Gridzellen
        grid.setPadding(new Insets(25, 25, 25, 25));        //Position/Abstand vom Ankerpunkt
        //grid.setGridLinesVisible(true);

        Scene scene = new Scene(grid, 1200, 400);           //Grid wird der Scene zugeordnet und das Fenster bekommt eine Größe
        primaryStage.setScene(scene);                       //Scene wird dem Fenster zugefügt

        Text scenetitle = new Text("Kleines Textadventure");                //Titel der Scene
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));     //Schriftart des Titels
        grid.add(scenetitle, 0, 0, 2, 1);                                   //Titel wird einer Stelle im Grid hinzugefügt

        beschreibung = new Text(abschnitte[aktuellerAbschnitt]);            //Beschreibung wird auf den ersten Text gesetzt
        grid.add(beschreibung, 0, 1, 2, 2);                                 //Beschreibung wird einer Stelle im Grid hinzugefügt

        button1 = new Button(beschriftungen[aktuellerAbschnitt][0]);
        grid.add(button1, 0, 3);
        grid.setHalignment(button1, HPos.RIGHT);                            //Button erstellt, beschriftet und hinzugefügt

        button2 = new Button(beschriftungen[aktuellerAbschnitt][1]);
        grid.add(button2, 1, 3);
        grid.setHalignment(button2, HPos.LEFT);

        button1.setOnAction(new EventHandler<ActionEvent>() 
        {
            @Override
            public void handle(ActionEvent e) 
            {
                aktuellerAbschnitt = naechsterAbschnitt[aktuellerAbschnitt][0];
                rewriteEntries(aktuellerAbschnitt);
            }
        });

        button2.setOnAction(new EventHandler<ActionEvent>() 
        {
            @Override
            public void handle(ActionEvent e) 
            {
                aktuellerAbschnitt = naechsterAbschnitt[aktuellerAbschnitt][1];
                rewriteEntries(aktuellerAbschnitt);
            }
        });

        primaryStage.show();
    }

    private void rewriteEntries(int abschnnitt)
    {
        beschreibung.setText(abschnitte[aktuellerAbschnitt]);
        button1.setText(beschriftungen[aktuellerAbschnitt][0]);
        button2.setText(beschriftungen[aktuellerAbschnitt][1]);     //Button und Beschriftung werden aktualisiert
    }

    public static void main(String[] args) 
    {
        launch(args);
    }

}

/**
 * Grundsätzlich nötig:
 * Textanzeige
 * Buttons für Auswahl der Antwort
 *
 * Texte aus Array von Abschnitten
 * Dazu Array mit Anzahl Buttons zum Abschnitt
 * Dazu Array mit Texten für jeden der Buttons
 */