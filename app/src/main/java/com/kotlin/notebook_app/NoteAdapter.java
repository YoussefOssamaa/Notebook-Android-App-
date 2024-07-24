package com.kotlin.notebook_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class NoteAdapter extends FirestoreRecyclerAdapter<Note , NoteAdapter.NoteViewHolder> {
    Context context ;

    public NoteAdapter(@NonNull FirestoreRecyclerOptions<Note> options , Context con) {
        super(options);
        this.context = con ;
    }


    @Override
    protected void onBindViewHolder(@NonNull NoteViewHolder holder, int i, @NonNull Note note) {
        holder.titleTextView.setText(note.title) ;
        holder.contentTextView.setText(note.content) ;
        holder.timestampTextView.setText(Utility.timestampToString(note.timestamp)) ;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_notes, parent , false) ;
        return new NoteViewHolder(view) ;
    }

    class NoteViewHolder extends RecyclerView.ViewHolder {

        TextView titleTextView , contentTextView, timestampTextView ;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.title_recycler_xml);
            contentTextView = itemView.findViewById(R.id.content_recycler_xml);
            timestampTextView = itemView.findViewById(R.id.timestamp_recycler_xml);
        }
    }

}
