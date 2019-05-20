<?php

use Illuminate\Http\Request;


Route::middleware('auth:api')->get('/user', function (Request $request) {
    return $request->user();
});

Route::post('/register','InscriptionController@validator');
Route::post('/login','ConnexionController@authenticate');
Route::get('/show_menu/{id}', 'MenuController@show_menu');
Route::get('/show_restaurant', 'RestaurantController@show_restaurant');
Route::get('/show_avis/{id}', 'AvisController@show_rest');

Route::group([
    'middleware' => 'auth:api',
], function() {
    Route::post('/create_restaurant','RestaurantController@create_restaurant');
    Route::post('/create_avis/{id}','AvisController@create_avis');
    Route::post('/create_menu/{id}','MenuController@create_menu');
    Route::put('/update_restaurant/{id}','RestaurantController@update_restaurant');
    Route::put('/update_avis/{id}','AvisController@update_avis');
    Route::put('/update_menu/{id}','MenuController@update_menu');
    Route::delete('/delete_restaurant/{id}','RestaurantController@delete_restaurant');
    Route::delete('/delete_menu/{id}','MenuController@delete_menu');
    Route::delete('/delete_avis/{id}','AvisController@destroy_avis');
    Route::get('/logout', 'ConnexionController@logout');
});