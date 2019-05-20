<?php

namespace App\Http\Controllers;

use App\User;
use Illuminate\Http\Request;
use App\Http\Controllers\Controller;
use Illuminate\Support\Facades\Hash;
use Illuminate\Support\Facades\Validator;
use Illuminate\Foundation\Auth\RegistersUsers;

class InscriptionController extends Controller
{
    protected function validator(Request $request)
    {
        $Validation = Validator::make($request->all(), ([
            'firstname' => 'required|string|max:255',
            'lastname' => 'required|string|max:255',
            'old' => 'required|integer|max:255',
            'email' => 'required|string|unique:users',
            'username' => 'required|string|unique:users',
            'password' => 'required|string',
            'phone' => 'required|string'
        ]));

        if ($Validation->fails()) {
            return response()->json($Validation->errors(), 400);
        } else {
            $data = $request->all();

            $data['password'] = bcrypt($data['password']);

            $user = User::create($data);

            return response()->json([
                'Success' => 'Save to database :)',
                'user' => $user
            ]);
        }
    }

//    protected function create(Request $request)
//    {
//        $user = new User();
//        $user->firstname = $request->get('firstname');
//        $user->lastname = $request->get('lastname');
//        $user->old = $request->get('old');
//        $user->email = $request->get('email');
//        $user->username = $request->get('username');
//        $user->password = bcrypt($request->get('password'));
//        $user->phone = $request->get('phone');
//        $user->save();
//    }
}
