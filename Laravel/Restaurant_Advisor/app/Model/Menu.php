<?php

namespace App\Model;

use Illuminate\Database\Eloquent\Model;

class Menu extends Model
{
    protected $table = 'menu';

    public function Restaurant() {
        return $this->hasMany(Restaurant::class);
    }
}
