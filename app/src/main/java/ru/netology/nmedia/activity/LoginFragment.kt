package ru.netology.nmedia.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.netology.nmedia.auth.AppAuth
import ru.netology.nmedia.databinding.FragmentLoginBinding
import ru.netology.nmedia.viewmodel.AuthViewModel
import javax.inject.Inject
import kotlin.getValue

@AndroidEntryPoint
class LoginFragment : Fragment() {

    @Inject
    lateinit var appAuth: AppAuth
    private val viewModel: AuthViewModel by viewModels()

    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.enterLogPass.setOnClickListener {
            val login = binding.login.text.toString()
            val password = binding.password.text.toString()
            viewModel.signIn(login, password)

            viewModel.dataState.observe(viewLifecycleOwner) { state ->
                if (state.loading) {
                } else if (state.error) {
                    Toast.makeText(context, "Ошибка авторизации", Toast.LENGTH_SHORT).show()
                } else {
                    viewModel.data.observe(viewLifecycleOwner) { auth ->
                        if (auth.id != 0L) findNavController().navigateUp()
                    }
                }
            }
        }
    }
}