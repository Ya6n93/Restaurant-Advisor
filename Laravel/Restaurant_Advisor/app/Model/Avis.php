<?php

namespace App\Model;

use Illuminate\Database\Eloquent\Model;

class Avis extends Model
{
    protected $table = "avis";

    public function Restaurant() {
        return $this->hasOne(Restaurant::class);
    }
}
