package Interfaces;

import org.json.JSONException;
import org.json.JSONObject;

public interface IToJson {
    // Esta interfaz es implementada por cualquier clase que necesita ser convertida a un JSON Object
    public JSONObject toJSON() throws JSONException;

    public void fromJSON(JSONObject jsonObject) throws JSONException;
}
