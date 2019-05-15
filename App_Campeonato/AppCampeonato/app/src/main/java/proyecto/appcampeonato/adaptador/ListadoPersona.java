package proyecto.appcampeonato.adaptador;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


import proyecto.appcampeonato.R;
import proyecto.appcampeonato.beans.PersonaBean;

public class ListadoPersona extends BaseAdapter {

    private Context context;
    private  int layout;
    private ArrayList<PersonaBean> persona;

    public ListadoPersona(Context context, int layout, ArrayList<PersonaBean> persona ) {
        this.layout = layout;
        this.persona = persona;
        this.context = context;
    }



    @Override
    public int getCount() {
        return persona.size();
    }

    @Override
    public Object getItem(int position) {
        return persona.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder{
        ImageView imgfotoitem;
        TextView txtnombre, txtapellido,txtcorreo;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View row = view;
        ViewHolder holder = new ViewHolder();

        if(row == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);

            holder.txtnombre = (TextView) row.findViewById(R.id.txtItemN);
            holder.txtapellido = (TextView) row.findViewById(R.id.txtItemA);
            holder.txtcorreo = (TextView) row.findViewById(R.id.txtItemEm);
            holder.imgfotoitem = (ImageView) row.findViewById(R.id.imgItemP);
            row.setTag(holder);
        }
        else {
            holder = (ViewHolder) row.getTag();
        }

        PersonaBean per = persona.get(position);

        holder.txtnombre.setText(per.getNombres());
        holder.txtapellido.setText(per.getApaterno());
        holder.txtcorreo.setText(per.getEmail());


        byte[] foto = per.getFoto();
        Bitmap bitmap = BitmapFactory.decodeByteArray(foto, 0, foto.length);
        holder.imgfotoitem.setImageBitmap(bitmap);

        return row;
    }
}
