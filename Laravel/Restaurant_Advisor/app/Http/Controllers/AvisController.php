<?php

namespace App\Http\Controllers;

use App\Model\Avis;
use App\Model\Restaurant;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Facades\Validator;

class AvisController extends Controller
{
    public function create_avis($id ,Request $request) {
        if (Auth::user()) {
            $rest = Restaurant::where('id', $id)->first();
            $Verification = Validator::make($request->all(), [
                'avis' => 'required|string'
            ]);

            if ($Verification->fails()) {
                return response()->json([
                    "Validator" => false,
                ]);
            }

            $avis = new Avis();
            $avis->avis = $request->avis;
            $avis->user_id = $request->user()->id;
            $avis->restaurant_id = $rest->id;
            $avis->save();

            return response()->json([
                "created"
            ]);
        } else {
            return response()->json([
                "Auth" => false,
            ]);
        }
    }

    public function show_rest($id, Avis $avis) {
        $rest = Restaurant::where('id', $id)->first();

        if ($rest) {
            $Avis = $avis->all()->where('restaurant_id', $id);

            if ($Avis) {
                return response()->json([
                    $Avis
                ]);
            } else {
                return response()->json([
                    "Avis" => False,
                ]);
            }
        } else {
            return response()->json([
                "Menu" => False,
            ]);
        }
    }

    public function update_avis($id, Request $request) {
        if (Auth::user()) {
            $Avis = Avis::where('id', $id)->first();
            $Verification = Validator::make($request->all(), [
                'avis' => 'required|string'
            ]);

            if ($Verification->fails()) {
                return response()->json([
                    "Validator" => false,
                ]);
            }

            if ($Avis->user_id == $request->user()->id) {
                $Avis->avis = $request->avis;
                $Avis->update();
                return response()->json([
                    "Updated"
                ]);
            } else {
                return response()->json([
                    "Access denied"
                ]);
            }
        } else {
            return response()->json([
                "User not found"
            ]);
        }
    }

    public function destroy_avis($id, Request $request, Avis $avis) {
        if (Auth::user()) {
            $Avis = $avis->where('id', $id)->first();
            if ($Avis->User_id == $request->user()->id) {
                $Avis->delete();
                return response()->json([
                    "destroy" => true,
                ]);
            } else {
                    return response()->json([
                        "Access" => False,
                    ]);
                }
        } else {
            return response()->json([
                "Auth denied"
            ]);
        }
    }
}
