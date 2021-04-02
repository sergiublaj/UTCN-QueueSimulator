package validator;

public class DurationFormatter {
   public static String getTimerFormat(int countValue) {
      int minutes = countValue / 60;
      int seconds = countValue % 60;
      return String.format("%02d:%02d", minutes, seconds);
   }
}
