package profileuser;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookapp.Book;
import com.example.bookapp.R;

import java.util.List;

public class listitem_adapter extends RecyclerView.Adapter<listitem_adapter.BookViewHolder> {
    Context context;
    List<Book_profile> books;

    public listitem_adapter(Context context) {
        this.context = context;
    }
    public void setData(List<Book_profile> list){
        this.books = list;
        notifyDataSetChanged();//load dữ liệu lên adapter
    }
    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_profile,parent,false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book_profile book = books.get(position);
        if(book == null){
            return;
        }
        holder.imgbook.setImageResource(book.getImgbook());
        holder.eye.setImageResource(book.getEye());
        holder.star.setImageResource(book.getStar());
        holder.chuong.setImageResource(book.getChuong());

        holder.tensach.setText(book.getTensach());
        holder.texteye.setText(book.getTexteye());
        holder.textstar.setText(book.getTextstar());
        holder.textchuong.setText(book.getTextchuong());
        holder.mota.setText(book.getMota());
    }

    @Override
    public int getItemCount() {
        if(books != null)
        {
            return  books.size();
        }
        return 0;
    }

    public class  BookViewHolder extends RecyclerView.ViewHolder{

        private TextView tensach, texteye, textstar, textchuong, mota;
        private ImageView imgbook, eye, star, chuong;
        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            imgbook = itemView.findViewById(R.id.imgbook);
            eye = itemView.findViewById(R.id.eye);
            star = itemView.findViewById(R.id.star);
            chuong = itemView.findViewById(R.id.chuong);

            tensach = itemView.findViewById(R.id.tensach);
            texteye = itemView.findViewById(R.id.texteye);
            textstar = itemView.findViewById(R.id.textstar);
            textchuong = itemView.findViewById(R.id.textchuong);
            mota = itemView.findViewById(R.id.mota);
        }
    }
}
