<?php

namespace App\Http\Controllers\Auth;

use App\User;
use App\Http\Controllers\Controller;
use http\Env\Request;
use Illuminate\Support\Facades\Hash;
use Illuminate\Support\Facades\Validator;
use Illuminate\Foundation\Auth\RegistersUsers;

class RegisterController extends Controller
{
    protected function validator(Request $request)
    {
        $Validation = Validator::make($request->all(), ([
            'firstname' => 'required|string|max:255',
            'lastname' => 'required|string|max:255',
            'old' => 'required|integer|max:255',
            'email' => 'required|string|unique:users',
            'username' => 'required|string|unique:users',
            'password' => 'required|string|confirmed',
            'phone' => 'required|regex:/(06)[0-9]{8}/',
        ]));

        if ($Validation->fails()) {
            return response()->json($Validation->errors());
        } else {
            return response()->json([
                'Success' => 'success',
                'firstname' => $request->get('firstname'),
                'lastname' => $request->get('lastname'),
                'old' => $request->get('old'),
                'email' => $request->get('email'),
                'username' => $request->get('username'),
                'password' => $request->get('password'),
                'phone' => $request->get('phone'),
            ]);
        }
    }

    protected function create(array $Validation)
    {
        return User::create([
            'name' => $Validation['name'],
            'email' => $Validation['email'],
            'password' => Hash::make($Validation['password']),
        ]);
    }
}
