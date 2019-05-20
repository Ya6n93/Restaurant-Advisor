<?php

namespace App\Http\Controllers;

use App\Model\Restaurant;
use App\User;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Facades\Validator;

class RestaurantController extends Controller
{
    protected function show_restaurant() {
        $rest = Restaurant::all();
        return response()->json([
            $rest
        ]);
    }

    protected function create_restaurant(Request $request)
    {
        if (Auth::user()) {
            $Validation = Validator::make($request->all(), ([
                'name' => 'required|string|max:255',
                'description' => 'required|string|max:255',
                'localisation' => 'required|string|max:255',
                'phone' => 'required|string',
                'website' => 'required|string|max:255',
                'planning' => 'required|string|max:255',
            ]));

            if ($Validation->fails()) {
                return response()->json($Validation->errors());
            } else {
                //$this->create_restaurant($request->all());
                $user = new Restaurant();
                $user->name = $request->get('name');
                $user->description = $request->get('description');
                $user->localisation = $request->get('localisation');
                $user->phone = $request->get('phone');
                $user->website = $request->get('website');
                $user->planning = $request->get('planning');
                $user->user_id = Auth::user()->id;
                $user->save();
                return response()->json([
                    'Success' => 'Save to database :)',
                    'name' => $request->get('name'),
                    'description' => $request->get('description'),
                    'localisation' => $request->get('localisation'),
                    'phone' => $request->get('phone'),
                    'website' => $request->get('website'),
                    'planning' => $request->get('planning'),
                ]);
            }
        } else {
            return response()->json([
                "Unauthorized"
            ]);
        }
    }

    protected function delete_restaurant($id) {

        if(Auth::user()) {
          $rest = Restaurant::where('id', $id)->first();
          if($rest->user_id == Auth::user()->id) {
              $rest->delete();
              return response()->json([
                  "Deleted"
              ]);
          } else {
              return response()->json([
                  "You Are Unauthorized"
              ]);
          }
        } else {
            return response()->json([
                "Unauthorized"
            ]);
        }
    }

    protected function update_restaurant($id, Request $request) {
        if (Auth::user()) {
            $Validation = Validator::make($request->all(), ([
                'name' => 'required|string|max:255|unique:restaurant',
                'description' => 'nullable|string|max:255',
                'localisation' => 'nullable|string|max:255',
                'phone' => 'nullable|regex:/(01)[0-9]{8}/',
                'website' => 'nullable|string|max:255',
                'planning' => 'nullable|string|max:255',
            ]));

            if ($Validation->fails()) {
                return response()->json($Validation->errors());
            } else {
                $rest = Restaurant::where([['id', $id], ['user_id', Auth::user()->id]])->first();
                if($rest) {
                    $rest->name = $request->get('name');
                    $rest->description = $request->get('description');
                    $rest->localisation = $request->get('localisation');
                    $rest->phone = $request->get('phone');
                    $rest->website = $request->get('website');
                    $rest->planning = $request->get('planning');
                    $rest->update();
                    return response()->json([
                        'Success' => 'Save to database :)',
                        'name' => $request->get('name'),
                        'description' => $request->get('description'),
                        'localisation' => $request->get('localisation'),
                        'phone' => $request->get('phone'),
                        'website' => $request->get('website'),
                        'planning' => $request->get('planning'),
                    ]);
                } else {
                    return response()->json([
                        "You Are not Unauthorized"
                    ]);
                }
            }
        } else {
            return response()->json([
                "Unauthorized"
            ]);
        }
    }
}
