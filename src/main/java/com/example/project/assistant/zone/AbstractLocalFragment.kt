package com.example.project.assistant.zone

//import kotlinx.android.synthetic.main.itemzone.view.*
//import kotlinx.android.synthetic.main.zonefragment.*
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project.R
import com.example.project.assistant.AssistantViewModel
import com.example.project.assistant.model.Local
import com.example.project.databinding.ZonefragmentBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
abstract class AbstractLocalFragment : Fragment(), (Local) -> Unit {
    //private var first = true
    private val zoneAdapter=LocalAdapter(this)
    val viewModel: AssistantViewModel by activityViewModels()
    lateinit var binding: ZonefragmentBinding
    val test =DataBindingUtil.inflate<ZonefragmentBinding>(layoutInflater, R.layout.zonefragment, this.view as ViewGroup?,false)
    val binding1 : ZonefragmentBinding by lazy {
        DataBindingUtil.inflate<ZonefragmentBinding>(layoutInflater, R.layout.zonefragment, this.view as ViewGroup?,false).apply {
            lifecycleOwner=this@AbstractLocalFragment

        }
    }
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding= ZonefragmentBinding.inflate(inflater,container,false)
        binding.lifecycleOwner=this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.zoneList.adapter=zoneAdapter
        binding.zoneList.layoutManager = LinearLayoutManager(this.requireContext())

        localToFindInitialisation()

        viewModel.localListe.observe(viewLifecycleOwner){
            Log.d("debug","$it")
            zoneAdapter.submitList(it)
            //it.let (zoneAdapter::submitList )
        }
        /*ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                TODO("Not yet implemented")
            }
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                Log.d("onSwiped", "${viewHolder.bindingAdapterPosition}")
                viewModel.zoneCode.value = viewHolder.itemView.textViewZone.text.toString()
                //findNavController().navigate(R.id.action_zoneFragment_to_immeubleFragment)
            }
        }).attachToRecyclerView(this.zone_list)*/
    }
    override fun invoke(zone: Local) {
      prepareGoTo(zone)
    }
    open fun localToFindInitialisation(){
        /*viewModel.localToFind.apply {
            this.type="immeuble"
            this.immeuble=null
            this.etage=null
            this.designation=null
        }*/
    }
    open fun prepareGoTo(zone : Local){
        /*Log.d("click", "${zone.designation} clicked")
        viewModel.localToFind.apply {
            Log.d("click", "before  : $this")
            //this.Zone=zone.designation
            this.immeuble=zone.immeuble
            this.type="etage"
            Log.d("click", "after : $this")
        }*/
        //findNavController().navigate(R.id.zoneFragment) // .navigate(R.id.action_zoneFragment_to_immeubleFragment)
    }

}