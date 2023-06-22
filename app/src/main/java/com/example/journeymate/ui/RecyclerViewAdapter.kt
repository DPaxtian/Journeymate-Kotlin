
import android.content.Context
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.journeymate.R
import com.example.journeymate.models.Routine
import org.w3c.dom.Text

class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    var routines : MutableList<Routine> = ArrayList()
    lateinit var context : Context
    var onRoutineClick : ((Routine) -> Unit)? = null
    var onFollowClick : ((Routine) -> Unit)? = null

    fun RecyclerViewAdapter(routines : MutableList<Routine>, context : Context){
        this.routines = routines
        this.context = context
    }

    class ViewHolder (view : View) : RecyclerView.ViewHolder(view) {
        val title: TextView
        val location : TextView
        val description : TextView
        val creator : TextView
        val follows : TextView
        val budget: TextView
        val followButton : ImageButton

        init {
            title = view.findViewById(R.id.card_title)
            location = view.findViewById(R.id.card_location)
            description = view.findViewById(R.id.card_description)
            creator = view.findViewById(R.id.card_username)
            follows = view.findViewById(R.id.card_follows)
            budget = view.findViewById(R.id.card_budget)
            followButton = view.findViewById(R.id.follow_routine)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.routine_card, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = routines.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val routine = routines[position]
        holder.title.text = routines[position].name
        holder.location.text = routines[position].city + ", " + routines[position].country
        holder.description.text = routines[position].routine_description
        holder.creator.text = routines[position].routine_creator
        holder.follows.text = routines[position].followers.toString()
        holder.budget.text = "0"

        holder.itemView.setOnClickListener {
            onRoutineClick?.invoke(routine)
        }

        holder.followButton.setOnClickListener {
            onFollowClick?.invoke(routine)
        }
    }
}