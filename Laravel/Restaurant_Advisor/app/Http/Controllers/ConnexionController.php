<?php

namespace App\Http\Controllers;

use Carbon\Carbon;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;


class ConnexionController extends Controller
{
    public function authenticate(Request $request) {
        $auth = request(['username', 'password']);
        if(!Auth::attempt($auth))
            return response()->json([
                'message' => 'Unauthorized'
            ], 401);

        $user = $request->user();
        $tokenResult = $user->createToken('Token');
        $token = $tokenResult->token;
        $token->save();

            return response()->json([
               'Successful' => 'You are connected',
               'User_id' => $request->user()->id,
               'Token' => $tokenResult->accessToken,
                'expires_at' => Carbon::parse(
                    $tokenResult->token->expires_at
                )->toDateTimeString()
            ]);
    }

    public function logout(Request $request) {
        $request->user()->token()->revoke();

        return response()->json([
            "Logout"
        ],200);
    }
}
