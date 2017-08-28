package io.karmanov.challenge;

public class TestDataProvider {

    public static final String XML_NORMAL_RECORD = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><msg><id value=\"962b23b55c2a3e13a1e52c1fae2ed73b\"/></msg>";
    public static final String XML_DONE_RECORD = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><msg><done/></msg>";
    public static final String XML_BAD_FORMATTED_RECORD = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><msg><0CKN008MVCHTCMICHQY1JJ3FMIP4O1MPDXZLAKRI08XU1XW9JQDT6CHOGROJWBDJ3EXZGQVAHM</foo></msg>";

    public static final String JSON_NORMAL_RECORD = "{\"status\": \"ok\", \"id\": \"e5bb2617a72123c995dfe96eeff6f692\"}";
    public static final String JSON_DONE_RECORD = "{\"status\": \"done\"}";
    public static final String JSON_BAD_FORMATTED_RECORD = "{\"status\": \"ok\", \"id\": [8VP7GRAT5K6SUKG3UGMIFL4QERDKLNN1AIETTEUE3S4VBR7P7KPR77PXIOV2OBOQDOOMKK81IUE8YGIO7MG11VA8MFVA3 [}";


}
