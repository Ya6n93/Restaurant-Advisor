<?php

namespace App\Http\Controllers;

use App\Model\Menu;
use App\Model\Restaurant;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Facades\Validator;

class MenuController extends Controller
{
    protected function show_menu($id) {
        $id_menu = Menu::where('restaurant_id', $id)->get();
        return response()
            ->json($id_menu, 200, ['Content-type'=> 'application/json; charset=utf-8'], JSON_UNESCAPED_UNICODE);
        //$rest = Restaurant::where('id', $id)->first();

//        if ($rest) {
//            $menus = Menu::all()->where('restaurant_id', $rest->id);
//
//            return response()->json(
//                $menus
//            );
//        } else {
//            return response()->json([
//                "Restaurant not found"
//            ]);
//        }
    }

    protected function create_menu($id, Request $request) {
        if (Auth::user()) {

            $Validation = Validator::make($request->all(), ([
                'name' => 'required|string|max:255|unique:menu',
                'description' => 'required|string|max:255',
                'price' => 'required|integer',
            ]));

            if ($Validation->fails()) {
                return response()->json($Validation->errors());
            } else {
                $rest = Restaurant::where([['id', $id], ['user_id', Auth::user()->id]])->first();
                if ($rest) {
                    $menu = new Menu();
                    $menu->name = $request->get('name');
                    $menu->description = $request->get('description');
                    $menu->price = $request->get('price');
                    $menu->restaurant_id = $rest->id;
                    $menu->save();
                    return response()->json([
                        'Success' => 'Save to database :)',
                        'name' => $request->get('name'),
                        'description' => $request->get('description'),
                        'price' => $request->get('price')
                    ]);
                } else {
                    return response()->json([
                        "You Are Unauthorized"
                    ]);
                }
            }
        } else {
            return response()->json([
                "Unauthorized"
            ]);
        }
    }

    protected function update_menu($id, Request $request) {
        if (Auth::user()) {
            $Validation = Validator::make($request->all(), ([
                'name' => 'required|string|max:255|unique:menu',
                'description' => 'required|string|max:255',
                'price' => 'required|integer',
            ]));

            if ($Validation->fails()) {
                return response()->json($Validation->errors());
            } else {
                $menu = Menu::where('id', $id)->first();
                $rest = Restaurant::where([['id', $menu->restaurant_id], ['user_id', Auth::user()->id]])->first();
                if ($rest) {

                    if ($menu->restaurant_id == $rest->id) {
                        $menu->name = $request->get('name');
                        $menu->description = $request->get('description');
                        $menu->price = $request->get('price');
                        $menu->restaurant_id = $rest->id;
                        $menu->update();
                        return response()->json([
                            'Success' => 'Save to database :)',
                            'name' => $request->get('name'),
                            'description' => $request->get('description'),
                            'price' => $request->get('price')
                        ]);
                    } else {
                        return response()->json([
                            "You Are Unauthorized in this menu"
                        ]);
                    }
                } else {
                    return response()->json([
                        "You Are Unauthorized"
                    ]);
                }
            }
        } else {
            return response()->json([
                "Unauthorized"
            ]);
        }
    }

    protected function delete_menu($id) {
        if (Auth::user()) {
            $menu = Menu::where('id', $id)->first();
            if ($menu) {
                $rest = Restaurant::where('id', $menu->restaurant_id)->first();
                if ($rest->user_id == Auth::user()->id) {
                    $menu->delete();
                    return response()->json([
                        "Deleted"
                    ]);
                } else {
                    return response()->json([
                        "Delete Unauthorized"
                    ]);
                }
            } else {
                return response()->json([
                    "Menu not found"
                ]);
            }
        } else {
            return response()->json([
                "Unauthorized"
            ]);
        }
    }
}
