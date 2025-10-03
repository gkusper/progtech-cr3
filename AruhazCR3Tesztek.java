import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.List;
import org.aruhaz.*;

class AruhazCR3Tesztek {
    static Aruhaz target;
    static Idoszak normal;
    @BeforeAll
    public static void initAruhaz() {
        target = new Aruhaz();
        normal = new Idoszak("Normál");
        normal.setEgysegAr(Termek.ALMA, 500.0);
        normal.setEgysegAr(Termek.BANAN, 450.0);
        normal.setKedvezmeny(Termek.ALMA, 5.0, 0.1);
        normal.setKedvezmeny(Termek.ALMA, 20.0, 0.15);
        normal.setKedvezmeny(Termek.BANAN, 2.0, 0.1);
        target.addIdoszak(normal);
    }
    @Test
    void teszt_cr3_pelda1_csakUltramax() {
        Kosar kosar = new Kosar(List.of(new Tetel(Termek.ALMA, 3.0)));
        ArInfo ar = target.getKosarAr(kosar, normal, List.of("KUPON-2000-ULTRAMAX"));
        assertEquals(0.0, ar.getAr(), 0.001);
        assertEquals(List.of(), ar.getFelNemHasznaltKuponok());
    }
    @Test
    void teszt_cr3_pelda2_a10plusUltramax() {
        Kosar kosar = new Kosar(List.of(new Tetel(Termek.ALMA, 4.1)));
        ArInfo ar = target.getKosarAr(kosar, normal, List.of("A10", "KUPON-2000-ULTRAMAX"));
        assertEquals(0.0, ar.getAr(), 0.001);
        assertEquals(List.of(), ar.getFelNemHasznaltKuponok());
    }
    @Test
    void teszt_cr3_pelda2b_Ultramaxplusa10() {
        Kosar kosar = new Kosar(List.of(new Tetel(Termek.ALMA, 4.1)));
        ArInfo ar = target.getKosarAr(kosar, normal, List.of("KUPON-2000-ULTRAMAX", "A10"));
        assertEquals(0.0, ar.getAr(), 0.001);
        assertEquals(List.of(), ar.getFelNemHasznaltKuponok());
    }
    @Test
    void teszt_cr3_pelda3_ketUltramax() {
        Kosar kosar = new Kosar(List.of(new Tetel(Termek.BANAN, 7.0)));
        ArInfo ar = target.getKosarAr(kosar, normal, List.of("KUPON-2000-ULTRAMAX", "KUPON-2000-ULTRAMAX"));
        assertEquals(0.0, ar.getAr(), 0.001);
    }
    @Test
    void teszt_cr3_pelda4_a10esUltramax() {
        Kosar kosar = new Kosar(List.of(new Tetel(Termek.ALMA, 1.5)));
        ArInfo ar = target.getKosarAr(kosar, normal, List.of("A10", "KUPON-2000-ULTRAMAX"));
        assertEquals(0.0, ar.getAr(), 0.001);
    }
    @Test
    void teszt_cr3_pelda5_ketTermek_egyKupon() {
        Kosar kosar = new Kosar(List.of(
                new Tetel(Termek.ALMA, 1.0),
                new Tetel(Termek.BANAN, 1.0)
        ));
        ArInfo ar = target.getKosarAr(kosar, normal, List.of("A5", "KUPON-2000-ULTRAMAX"));
        assertEquals(0.0, ar.getAr(), 0.001);
    }
    @Test
    void teszt_cr3_pelda6_b10Hasznalhatatlan() {
        Kosar kosar = new Kosar(List.of(new Tetel(Termek.ALMA, 1.0)));
        ArInfo ar = target.getKosarAr(kosar, normal, List.of("B10", "KUPON-2000-ULTRAMAX"));
        assertEquals(0.0, ar.getAr(), 0.001);
        assertEquals(List.of("B10"), ar.getFelNemHasznaltKuponok());
    }
    @Test
    void teszt_cr3_pelda7_nemNullaVegosszeg() {
        Kosar kosar = new Kosar(List.of(new Tetel(Termek.ALMA, 10.0)));
        ArInfo ar = target.getKosarAr(kosar, normal, List.of("KUPON-2000-ULTRAMAX"));
        assertEquals(2500.0, ar.getAr(), 0.001);
    }
    @Test
    void teszt_cr3_pelda8_banankupac() {
        Kosar kosar = new Kosar(List.of(new Tetel(Termek.BANAN, 15.0)));
        ArInfo ar = target.getKosarAr(kosar, normal, List.of("KUPON-2000-ULTRAMAX"));
        assertEquals(4075.0, ar.getAr(), 0.001);
    }
    @Test
    void teszt_cr3_pelda9_ketUltramax_nemNulla() {
        Kosar kosar = new Kosar(List.of(new Tetel(Termek.BANAN, 10.0)));
        ArInfo ar = target.getKosarAr(kosar, normal, List.of("KUPON-2000-ULTRAMAX", "KUPON-2000-ULTRAMAX"));
        assertEquals(50.0, ar.getAr(), 0.001);
    }
    @Test
    void teszt_cr3_pelda10_kritikusSorrend() {
        Kosar kosar = new Kosar(List.of(
                new Tetel(Termek.ALMA, 2.0),
                new Tetel(Termek.BANAN, 2.0)
        ));
        ArInfo ar = target.getKosarAr(kosar, normal, List.of("KUPON-2000-ULTRAMAX", "A5"));
        assertEquals(0.0, ar.getAr(), 0.001);
    }
}